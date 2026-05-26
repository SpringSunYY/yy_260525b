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
import com.lz.manage.mapper.CourseInfoMapper;
import com.lz.manage.model.domain.CourseInfo;
import com.lz.manage.service.ICourseInfoService;
import com.lz.manage.model.dto.courseInfo.CourseInfoQuery;
import com.lz.manage.model.vo.courseInfo.CourseInfoVo;

/**
 * 课程信息Service业务层处理
 *
 * @author YY
 * @date 2026-05-26
 */
@Service
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, CourseInfo> implements ICourseInfoService
{

    @Resource
    private CourseInfoMapper courseInfoMapper;

    //region mybatis代码
    /**
     * 查询课程信息
     *
     * @param id 课程信息主键
     * @return 课程信息
     */
    @Override
    public CourseInfo selectCourseInfoById(Long id)
    {
        return courseInfoMapper.selectCourseInfoById(id);
    }

    /**
     * 查询课程信息列表
     *
     * @param courseInfo 课程信息
     * @return 课程信息
     */
    @Override
    public List<CourseInfo> selectCourseInfoList(CourseInfo courseInfo)
    {
        return courseInfoMapper.selectCourseInfoList(courseInfo);
    }

    /**
     * 新增课程信息
     *
     * @param courseInfo 课程信息
     * @return 结果
     */
    @Override
    public int insertCourseInfo(CourseInfo courseInfo)
    {
        courseInfo.setCreateTime(DateUtils.getNowDate());
        return courseInfoMapper.insertCourseInfo(courseInfo);
    }

    /**
     * 修改课程信息
     *
     * @param courseInfo 课程信息
     * @return 结果
     */
    @Override
    public int updateCourseInfo(CourseInfo courseInfo)
    {
        courseInfo.setUpdateTime(DateUtils.getNowDate());
        return courseInfoMapper.updateCourseInfo(courseInfo);
    }

    /**
     * 批量删除课程信息
     *
     * @param ids 需要删除的课程信息主键
     * @return 结果
     */
    @Override
    public int deleteCourseInfoByIds(Long[] ids)
    {
        return courseInfoMapper.deleteCourseInfoByIds(ids);
    }

    /**
     * 删除课程信息信息
     *
     * @param id 课程信息主键
     * @return 结果
     */
    @Override
    public int deleteCourseInfoById(Long id)
    {
        return courseInfoMapper.deleteCourseInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<CourseInfo> getQueryWrapper(CourseInfoQuery courseInfoQuery){
        QueryWrapper<CourseInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = courseInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = courseInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String courseType = courseInfoQuery.getCourseType();
        queryWrapper.eq(StringUtils.isNotEmpty(courseType) ,"course_type",courseType);

        String courseName = courseInfoQuery.getCourseName();
        queryWrapper.like(StringUtils.isNotEmpty(courseName) ,"course_name",courseName);

        String status = courseInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Long userId = courseInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = courseInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CourseInfoVo> convertVoList(List<CourseInfo> courseInfoList) {
        if (StringUtils.isEmpty(courseInfoList)) {
            return Collections.emptyList();
        }
        return courseInfoList.stream().map(CourseInfoVo::objToVo).collect(Collectors.toList());
    }
}
