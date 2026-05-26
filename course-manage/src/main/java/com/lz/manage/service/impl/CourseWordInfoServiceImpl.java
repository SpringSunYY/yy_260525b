package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.enums.CourseWorkStatusEnum;
import com.lz.manage.enums.ManageCourseStatusEnum;
import com.lz.manage.mapper.CourseWordInfoMapper;
import com.lz.manage.model.domain.CourseInfo;
import com.lz.manage.model.domain.CourseWordInfo;
import com.lz.manage.model.dto.courseWordInfo.CourseWordInfoQuery;
import com.lz.manage.model.vo.courseWordInfo.CourseWordInfoVo;
import com.lz.manage.service.ICourseInfoService;
import com.lz.manage.service.ICourseWordInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 课程作业Service业务层处理
 *
 * @author YY
 * @date 2026-05-26
 */
@Service
public class CourseWordInfoServiceImpl extends ServiceImpl<CourseWordInfoMapper, CourseWordInfo> implements ICourseWordInfoService {

    @Resource
    private CourseWordInfoMapper courseWordInfoMapper;

    @Resource
    private ICourseInfoService courseInfoService;

    @Resource
    private ISysUserService sysUserService;
    //region mybatis代码

    /**
     * 查询课程作业
     *
     * @param id 课程作业主键
     * @return 课程作业
     */
    @Override
    public CourseWordInfo selectCourseWordInfoById(Long id) {
        return courseWordInfoMapper.selectCourseWordInfoById(id);
    }

    /**
     * 查询课程作业列表
     *
     * @param courseWordInfo 课程作业
     * @return 课程作业
     */
    @Override
    public List<CourseWordInfo> selectCourseWordInfoList(CourseWordInfo courseWordInfo) {
        List<CourseWordInfo> courseWordInfos = courseWordInfoMapper.selectCourseWordInfoList(courseWordInfo);
        for (CourseWordInfo info : courseWordInfos) {
            CourseInfo courseInfo = courseInfoService.selectCourseInfoById(info.getCourseId());
            if (StringUtils.isNotNull(courseInfo)) {
                info.setCourseName(courseInfo.getCourseName());
            }
            SysUser teacherUser = sysUserService.selectUserById(info.getTeacherId());
            if (StringUtils.isNotNull(teacherUser)) {
                info.setTeacherName(teacherUser.getUserName());
            }
        }
        return courseWordInfos;
    }

    /**
     * 新增课程作业
     *
     * @param courseWordInfo 课程作业
     * @return 结果
     */
    @Override
    public int insertCourseWordInfo(CourseWordInfo courseWordInfo) {     //查询课程是否存在
        initCourseWork(courseWordInfo);

        courseWordInfo.setStatus(CourseWorkStatusEnum.COURSE_WORK_STATUS_0.getValue());
        courseWordInfo.setCreateBy(SecurityUtils.getUsername());
        courseWordInfo.setCreateTime(DateUtils.getNowDate());
        return courseWordInfoMapper.insertCourseWordInfo(courseWordInfo);
    }

    /**
     * 修改课程作业
     *
     * @param courseWordInfo 课程作业
     * @return 结果
     */
    @Override
    public int updateCourseWordInfo(CourseWordInfo courseWordInfo) {
        //查询课程作业，如果已经结束不可修改
        CourseWordInfo workDb = courseWordInfoMapper.selectCourseWordInfoById(courseWordInfo.getId());
        ThrowUtils.throwIf(
                workDb.getStatus().equals(CourseWorkStatusEnum.COURSE_WORK_STATUS_1.getValue()),
                "课程作业已结束，不可修改");
        //如果课程和数据库不匹配
        ThrowUtils.throwIf(
                !workDb.getCourseId().equals(courseWordInfo.getCourseId()),
                "不可以修改作业课程"
        );
        initCourseWork(courseWordInfo);
        courseWordInfo.setUpdateTime(DateUtils.getNowDate());
        return courseWordInfoMapper.updateCourseWordInfo(courseWordInfo);
    }

    private void initCourseWork(CourseWordInfo courseWordInfo) {
        CourseInfo courseInfo = courseInfoService.selectCourseInfoById(courseWordInfo.getCourseId());
        ThrowUtils.throwIf(StringUtils.isNull(courseInfo), "课程不存在");
        //如果课程不是开启
        ThrowUtils.throwIf(
                !courseInfo.getStatus().equals(ManageCourseStatusEnum.MANAGE_COURSE_STATUS_1.getValue()),
                "课程已关闭");
        courseWordInfo.setTeacherId(courseInfo.getUserId());

        //默认开始时间为当前时间
        Date nowDate = DateUtils.getNowDate();
        courseWordInfo.setStartTime(nowDate);
        //结束时间不能小于开始时间
        ThrowUtils.throwIf(
                courseWordInfo.getEndTime().before(courseWordInfo.getStartTime()),
                "结束时间不能小于当前时间或开始时间"
        );
    }

    /**
     * 批量删除课程作业
     *
     * @param ids 需要删除的课程作业主键
     * @return 结果
     */
    @Override
    public int deleteCourseWordInfoByIds(Long[] ids) {
        return courseWordInfoMapper.deleteCourseWordInfoByIds(ids);
    }

    /**
     * 删除课程作业信息
     *
     * @param id 课程作业主键
     * @return 结果
     */
    @Override
    public int deleteCourseWordInfoById(Long id) {
        return courseWordInfoMapper.deleteCourseWordInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<CourseWordInfo> getQueryWrapper(CourseWordInfoQuery courseWordInfoQuery) {
        QueryWrapper<CourseWordInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = courseWordInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = courseWordInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long courseId = courseWordInfoQuery.getCourseId();
        queryWrapper.eq(StringUtils.isNotNull(courseId), "course_id", courseId);

        String wordName = courseWordInfoQuery.getWordName();
        queryWrapper.like(StringUtils.isNotEmpty(wordName), "word_name", wordName);

        String status = courseWordInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Long teacherId = courseWordInfoQuery.getTeacherId();
        queryWrapper.eq(StringUtils.isNotNull(teacherId), "teacher_id", teacherId);

        Date createTime = courseWordInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CourseWordInfoVo> convertVoList(List<CourseWordInfo> courseWordInfoList) {
        if (StringUtils.isEmpty(courseWordInfoList)) {
            return Collections.emptyList();
        }
        return courseWordInfoList.stream().map(CourseWordInfoVo::objToVo).collect(Collectors.toList());
    }
}
