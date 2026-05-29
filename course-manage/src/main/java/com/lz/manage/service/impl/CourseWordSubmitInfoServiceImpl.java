package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.exception.ServiceException;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.enums.CourseWordSubmitReviewStatusEnum;
import com.lz.manage.enums.CourseWordSubmitStatusEnum;
import com.lz.manage.enums.CourseWorkStatusEnum;
import com.lz.manage.mapper.CourseWordSubmitInfoMapper;
import com.lz.manage.model.domain.CourseInfo;
import com.lz.manage.model.domain.CourseWordInfo;
import com.lz.manage.model.domain.CourseWordSubmitInfo;
import com.lz.manage.model.dto.courseWordSubmitInfo.CourseWordSubmitInfoQuery;
import com.lz.manage.model.vo.courseWordSubmitInfo.CourseWordSubmitInfoVo;
import com.lz.manage.service.ICourseInfoService;
import com.lz.manage.service.ICourseWordInfoService;
import com.lz.manage.service.ICourseWordSubmitInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 作业提交Service业务层处理
 *
 * @author YY
 * @date 2026-05-26
 */
@Service
public class CourseWordSubmitInfoServiceImpl extends ServiceImpl<CourseWordSubmitInfoMapper, CourseWordSubmitInfo> implements ICourseWordSubmitInfoService {

    @Resource
    private CourseWordSubmitInfoMapper courseWordSubmitInfoMapper;

    @Resource
    private ICourseInfoService courseInfoService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ICourseWordInfoService courseWordInfoService;

    //region mybatis代码

    /**
     * 查询作业提交
     *
     * @param id 作业提交主键
     * @return 作业提交
     */
    @Override
    public CourseWordSubmitInfo selectCourseWordSubmitInfoById(Long id) {
        return courseWordSubmitInfoMapper.selectCourseWordSubmitInfoById(id);
    }

    /**
     * 查询作业提交列表
     *
     * @param courseWordSubmitInfo 作业提交
     * @return 作业提交
     */
    @Override
    public List<CourseWordSubmitInfo> selectCourseWordSubmitInfoList(CourseWordSubmitInfo courseWordSubmitInfo) {
        //如果不是管理员且是老师
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())
                && SecurityUtils.hasRole("teacher")) {
            courseWordSubmitInfo.setTeacherId(SecurityUtils.getUserId());
        }
        //如果不是超级管理员是学生
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())
                && SecurityUtils.hasRole("student")) {
            courseWordSubmitInfo.setUserId(SecurityUtils.getUserId());
        }
        List<CourseWordSubmitInfo> courseWordSubmitInfos = courseWordSubmitInfoMapper.selectCourseWordSubmitInfoList(courseWordSubmitInfo);
        for (CourseWordSubmitInfo info : courseWordSubmitInfos) {
            CourseInfo courseInfo = courseInfoService.selectCourseInfoById(info.getCourseId());
            if (StringUtils.isNotNull(courseInfo)) {
                info.setCourseName(courseInfo.getCourseName());
            }
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            SysUser teacherUser = sysUserService.selectUserById(info.getTeacherId());
            if (StringUtils.isNotNull(teacherUser)) {
                info.setTeacherName(teacherUser.getUserName());
            }
            CourseWordInfo courseWordInfo = courseWordInfoService.selectCourseWordInfoById(info.getWordId());
            if (StringUtils.isNotNull(courseWordInfo)) {
                info.setWordName(courseWordInfo.getWordName());
            }
        }
        return courseWordSubmitInfos;
    }

    /**
     * 新增作业提交
     *
     * @param courseWordSubmitInfo 作业提交
     * @return 结果
     */
    @Override
    public int insertCourseWordSubmitInfo(CourseWordSubmitInfo courseWordSubmitInfo) {
        courseWordSubmitInfo.setCreateTime(DateUtils.getNowDate());
        return courseWordSubmitInfoMapper.insertCourseWordSubmitInfo(courseWordSubmitInfo);
    }

    /**
     * 修改作业提交
     *
     * @param courseWordSubmitInfo 作业提交
     * @return 结果
     */
    @Override
    public int updateCourseWordSubmitInfo(CourseWordSubmitInfo courseWordSubmitInfo) {
        //修改的时候看看课程作业是否存在
        CourseWordInfo courseWordInfo = courseWordInfoService.selectCourseWordInfoById(courseWordSubmitInfo.getWordId());
        ThrowUtils.throwIf(
                StringUtils.isNull(courseWordInfo),
                "课程作业不存在"
        );
        //如果结束时间+1天小于当前时间
        Date endTimePlusOneDay = new Date(courseWordInfo.getEndTime().getTime() + 24 * 60 * 60 * 1000);
        boolean before = endTimePlusOneDay.before(DateUtils.getNowDate());
        if (before) {
            courseWordSubmitInfo.setStatus(CourseWorkStatusEnum.COURSE_WORK_STATUS_1.getValue());
            courseWordSubmitInfoMapper.updateCourseWordSubmitInfo(courseWordSubmitInfo);
            throw new ServiceException("课程作业已结束");
        }
        //如果传过来的提交内容不为空
        if (StringUtils.isNotEmpty(courseWordSubmitInfo.getSubmitContent())
                || StringUtils.isNotEmpty(courseWordSubmitInfo.getSubmitFile())) {
            courseWordSubmitInfo.setStatus(CourseWordSubmitStatusEnum.COURSE_WORD_SUBMIT_STATUS_1.getValue());
        }
        //如果传过来的是已提交
        if (CourseWordSubmitStatusEnum.COURSE_WORD_SUBMIT_STATUS_1.getValue().equals(courseWordSubmitInfo.getStatus())) {
            courseWordSubmitInfo.setSubmitTime(new Date());
        }
        //如果传过来的分数不为空
        if (StringUtils.isNotNull(courseWordSubmitInfo.getScore())) {
            courseWordSubmitInfo.setReviewStatus(CourseWordSubmitReviewStatusEnum.COURSE_WORD_SUBMIT_REVIEW_STATUS_1.getValue());
        }
        //如果作业已经审批
        CourseWordSubmitInfo submitInfoDb = courseWordSubmitInfoMapper.selectCourseWordSubmitInfoById(courseWordSubmitInfo.getId());
        if (submitInfoDb.getReviewStatus().equals(CourseWordSubmitReviewStatusEnum.COURSE_WORD_SUBMIT_REVIEW_STATUS_1.getValue())) {
            throw new ServiceException("作业已经审批");
        }
        //如果传过来的是已审批
        if (CourseWordSubmitReviewStatusEnum.COURSE_WORD_SUBMIT_REVIEW_STATUS_1.getValue().equals(courseWordSubmitInfo.getReviewStatus())) {
            courseWordSubmitInfo.setReviewTime(new Date());
        }
        courseWordSubmitInfo.setUpdateTime(DateUtils.getNowDate());
        return courseWordSubmitInfoMapper.updateCourseWordSubmitInfo(courseWordSubmitInfo);
    }

    /**
     * 批量删除作业提交
     *
     * @param ids 需要删除的作业提交主键
     * @return 结果
     */
    @Override
    public int deleteCourseWordSubmitInfoByIds(Long[] ids) {
        return courseWordSubmitInfoMapper.deleteCourseWordSubmitInfoByIds(ids);
    }

    /**
     * 删除作业提交信息
     *
     * @param id 作业提交主键
     * @return 结果
     */
    @Override
    public int deleteCourseWordSubmitInfoById(Long id) {
        return courseWordSubmitInfoMapper.deleteCourseWordSubmitInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<CourseWordSubmitInfo> getQueryWrapper(CourseWordSubmitInfoQuery courseWordSubmitInfoQuery) {
        QueryWrapper<CourseWordSubmitInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = courseWordSubmitInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = courseWordSubmitInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long courseId = courseWordSubmitInfoQuery.getCourseId();
        queryWrapper.eq(StringUtils.isNotNull(courseId), "course_id", courseId);

        Long wordId = courseWordSubmitInfoQuery.getWordId();
        queryWrapper.eq(StringUtils.isNotNull(wordId), "word_id", wordId);

        String status = courseWordSubmitInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        String reviewStatus = courseWordSubmitInfoQuery.getReviewStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(reviewStatus), "review_status", reviewStatus);

        Long teacherId = courseWordSubmitInfoQuery.getTeacherId();
        queryWrapper.eq(StringUtils.isNotNull(teacherId), "teacher_id", teacherId);

        Long userId = courseWordSubmitInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = courseWordSubmitInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CourseWordSubmitInfoVo> convertVoList(List<CourseWordSubmitInfo> courseWordSubmitInfoList) {
        if (StringUtils.isEmpty(courseWordSubmitInfoList)) {
            return Collections.emptyList();
        }
        return courseWordSubmitInfoList.stream().map(CourseWordSubmitInfoVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public int authCourseWordSubmitInfo(CourseWordSubmitInfo courseWordSubmitInfo) {
        return this.updateCourseWordSubmitInfo(courseWordSubmitInfo);
    }
}
