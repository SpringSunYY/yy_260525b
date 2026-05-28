package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.enums.CourseRegisterStatusEnum;
import com.lz.manage.enums.ManageCourseStatusEnum;
import com.lz.manage.mapper.CourseRegisterInfoMapper;
import com.lz.manage.model.domain.CourseInfo;
import com.lz.manage.model.domain.CourseRegisterInfo;
import com.lz.manage.model.dto.courseRegisterInfo.CourseRegisterInfoQuery;
import com.lz.manage.model.vo.courseRegisterInfo.CourseRegisterInfoVo;
import com.lz.manage.service.ICourseInfoService;
import com.lz.manage.service.ICourseRegisterInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 课程注册Service业务层处理
 *
 * @author YY
 * @date 2026-05-26
 */
@Service
public class CourseRegisterInfoServiceImpl extends ServiceImpl<CourseRegisterInfoMapper, CourseRegisterInfo> implements ICourseRegisterInfoService {

    @Resource
    private CourseRegisterInfoMapper courseRegisterInfoMapper;

    @Resource
    private ICourseInfoService courseInfoService;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询课程注册
     *
     * @param id 课程注册主键
     * @return 课程注册
     */
    @Override
    public CourseRegisterInfo selectCourseRegisterInfoById(Long id) {
        return courseRegisterInfoMapper.selectCourseRegisterInfoById(id);
    }

    /**
     * 查询课程注册列表
     *
     * @param courseRegisterInfo 课程注册
     * @return 课程注册
     */
    @Override
    public List<CourseRegisterInfo> selectCourseRegisterInfoList(CourseRegisterInfo courseRegisterInfo) {
        List<CourseRegisterInfo> courseRegisterInfos = courseRegisterInfoMapper.selectCourseRegisterInfoList(courseRegisterInfo);
        for (CourseRegisterInfo info : courseRegisterInfos) {
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
        }
        return courseRegisterInfos;
    }

    /**
     * 新增课程注册
     *
     * @param courseRegisterInfo 课程注册
     * @return 结果
     */
    @Override
    public int insertCourseRegisterInfo(CourseRegisterInfo courseRegisterInfo) {
        //查询课程是否存在
        CourseInfo courseInfo = courseInfoService.selectCourseInfoById(courseRegisterInfo.getCourseId());
        ThrowUtils.throwIf(StringUtils.isNull(courseInfo), "课程不存在");
        //如果课程不是开启
        ThrowUtils.throwIf(
                !courseInfo.getStatus().equals(ManageCourseStatusEnum.MANAGE_COURSE_STATUS_1.getValue()),
                "课程已关闭");
        //查询是否已经注册
        List<CourseRegisterInfo> courseRegisterInfos = courseRegisterInfoMapper.selectList(new LambdaQueryWrapper<CourseRegisterInfo>()
                .eq(CourseRegisterInfo::getCourseId, courseRegisterInfo.getCourseId())
                .eq(CourseRegisterInfo::getUserId, courseRegisterInfo.getUserId()));
        ThrowUtils.throwIf(StringUtils.isNotEmpty(courseRegisterInfos), "已注册");
        //更新课程人数
        long count = this.count(new LambdaQueryWrapper<CourseRegisterInfo>()
                .eq(CourseRegisterInfo::getCourseId, courseRegisterInfo.getCourseId())
                .eq(CourseRegisterInfo::getStatus, CourseRegisterStatusEnum.COURSE_REGISTER_STATUS_0.getValue()));
        courseInfo.setRegisterNum(count + 1);
        courseInfoService.updateById(courseInfo);
        courseRegisterInfo.setStatus(CourseRegisterStatusEnum.COURSE_REGISTER_STATUS_0.getValue());
        courseRegisterInfo.setCreateBy(SecurityUtils.getUsername());
        courseRegisterInfo.setTeacherId(courseInfo.getUserId());
        courseRegisterInfo.setCreateTime(DateUtils.getNowDate());
        return courseRegisterInfoMapper.insertCourseRegisterInfo(courseRegisterInfo);
    }

    /**
     * 修改课程注册
     *
     * @param courseRegisterInfo 课程注册
     * @return 结果
     */
    @Override
    public int updateCourseRegisterInfo(CourseRegisterInfo courseRegisterInfo) {
        //查询课程是否存在
        CourseInfo courseInfo = courseInfoService.selectCourseInfoById(courseRegisterInfo.getCourseId());
        ThrowUtils.throwIf(StringUtils.isNull(courseInfo), "课程不存在");
        //如果课程不是开启
        ThrowUtils.throwIf(
                !courseInfo.getStatus().equals(ManageCourseStatusEnum.MANAGE_COURSE_STATUS_1.getValue()),
                "课程已关闭");
        //查询课程，如果已关闭不可以修改
        CourseRegisterInfo registerInfoDb = courseRegisterInfoMapper.selectById(courseRegisterInfo.getId());
        ThrowUtils.throwIf(
                registerInfoDb.getStatus().equals(CourseRegisterStatusEnum.COURSE_REGISTER_STATUS_1.getValue()),
                "已退课不可以修改"
        );
        //如果和课程不一样
        ThrowUtils.throwIf(
                !registerInfoDb.getCourseId().equals(courseRegisterInfo.getCourseId()),
                "课程不一致"
        );
        //更新课程人数
        long count = this.count(new LambdaQueryWrapper<CourseRegisterInfo>()
                .eq(CourseRegisterInfo::getCourseId, courseRegisterInfo.getCourseId())
                .eq(CourseRegisterInfo::getStatus, CourseRegisterStatusEnum.COURSE_REGISTER_STATUS_0.getValue()));
        courseInfo.setRegisterNum(count);
        courseInfoService.updateById(courseInfo);
        courseRegisterInfo.setUpdateBy(SecurityUtils.getUsername());
        courseRegisterInfo.setUpdateTime(DateUtils.getNowDate());
        return courseRegisterInfoMapper.updateCourseRegisterInfo(courseRegisterInfo);
    }

    /**
     * 批量删除课程注册
     *
     * @param ids 需要删除的课程注册主键
     * @return 结果
     */
    @Override
    public int deleteCourseRegisterInfoByIds(Long[] ids) {
        return courseRegisterInfoMapper.deleteCourseRegisterInfoByIds(ids);
    }

    /**
     * 删除课程注册信息
     *
     * @param id 课程注册主键
     * @return 结果
     */
    @Override
    public int deleteCourseRegisterInfoById(Long id) {
        return courseRegisterInfoMapper.deleteCourseRegisterInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<CourseRegisterInfo> getQueryWrapper(CourseRegisterInfoQuery courseRegisterInfoQuery) {
        QueryWrapper<CourseRegisterInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = courseRegisterInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = courseRegisterInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long courseId = courseRegisterInfoQuery.getCourseId();
        queryWrapper.eq(StringUtils.isNotNull(courseId), "course_id", courseId);

        String status = courseRegisterInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Long teacherId = courseRegisterInfoQuery.getTeacherId();
        queryWrapper.eq(StringUtils.isNotNull(teacherId), "teacher_id", teacherId);

        Long userId = courseRegisterInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = courseRegisterInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CourseRegisterInfoVo> convertVoList(List<CourseRegisterInfo> courseRegisterInfoList) {
        if (StringUtils.isEmpty(courseRegisterInfoList)) {
            return Collections.emptyList();
        }
        return courseRegisterInfoList.stream().map(CourseRegisterInfoVo::objToVo).collect(Collectors.toList());
    }
}
