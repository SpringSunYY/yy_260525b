package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.CourseRegisterInfoMapper;
import com.lz.manage.model.domain.CourseRegisterInfo;
import com.lz.manage.service.ICourseRegisterInfoService;
import com.lz.manage.model.dto.courseRegisterInfo.CourseRegisterInfoQuery;
import com.lz.manage.model.vo.courseRegisterInfo.CourseRegisterInfoVo;

/**
 * 课程注册Service业务层处理
 *
 * @author YY
 * @date 2026-05-26
 */
@Service
public class CourseRegisterInfoServiceImpl extends ServiceImpl<CourseRegisterInfoMapper, CourseRegisterInfo> implements ICourseRegisterInfoService
{

    @Resource
    private CourseRegisterInfoMapper courseRegisterInfoMapper;

    //region mybatis代码
    /**
     * 查询课程注册
     *
     * @param id 课程注册主键
     * @return 课程注册
     */
    @Override
    public CourseRegisterInfo selectCourseRegisterInfoById(Long id)
    {
        return courseRegisterInfoMapper.selectCourseRegisterInfoById(id);
    }

    /**
     * 查询课程注册列表
     *
     * @param courseRegisterInfo 课程注册
     * @return 课程注册
     */
    @Override
    public List<CourseRegisterInfo> selectCourseRegisterInfoList(CourseRegisterInfo courseRegisterInfo)
    {
        return courseRegisterInfoMapper.selectCourseRegisterInfoList(courseRegisterInfo);
    }

    /**
     * 新增课程注册
     *
     * @param courseRegisterInfo 课程注册
     * @return 结果
     */
    @Override
    public int insertCourseRegisterInfo(CourseRegisterInfo courseRegisterInfo)
    {
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
    public int updateCourseRegisterInfo(CourseRegisterInfo courseRegisterInfo)
    {
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
    public int deleteCourseRegisterInfoByIds(Long[] ids)
    {
        return courseRegisterInfoMapper.deleteCourseRegisterInfoByIds(ids);
    }

    /**
     * 删除课程注册信息
     *
     * @param id 课程注册主键
     * @return 结果
     */
    @Override
    public int deleteCourseRegisterInfoById(Long id)
    {
        return courseRegisterInfoMapper.deleteCourseRegisterInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<CourseRegisterInfo> getQueryWrapper(CourseRegisterInfoQuery courseRegisterInfoQuery){
        QueryWrapper<CourseRegisterInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = courseRegisterInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = courseRegisterInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long courseId = courseRegisterInfoQuery.getCourseId();
        queryWrapper.eq( StringUtils.isNotNull(courseId),"course_id",courseId);

        String status = courseRegisterInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Long teacherId = courseRegisterInfoQuery.getTeacherId();
        queryWrapper.eq( StringUtils.isNotNull(teacherId),"teacher_id",teacherId);

        Long userId = courseRegisterInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = courseRegisterInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

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
