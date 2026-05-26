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
import com.lz.manage.mapper.CourseWordInfoMapper;
import com.lz.manage.model.domain.CourseWordInfo;
import com.lz.manage.service.ICourseWordInfoService;
import com.lz.manage.model.dto.courseWordInfo.CourseWordInfoQuery;
import com.lz.manage.model.vo.courseWordInfo.CourseWordInfoVo;

/**
 * 课程作业Service业务层处理
 *
 * @author YY
 * @date 2026-05-26
 */
@Service
public class CourseWordInfoServiceImpl extends ServiceImpl<CourseWordInfoMapper, CourseWordInfo> implements ICourseWordInfoService
{

    @Resource
    private CourseWordInfoMapper courseWordInfoMapper;

    //region mybatis代码
    /**
     * 查询课程作业
     *
     * @param id 课程作业主键
     * @return 课程作业
     */
    @Override
    public CourseWordInfo selectCourseWordInfoById(Long id)
    {
        return courseWordInfoMapper.selectCourseWordInfoById(id);
    }

    /**
     * 查询课程作业列表
     *
     * @param courseWordInfo 课程作业
     * @return 课程作业
     */
    @Override
    public List<CourseWordInfo> selectCourseWordInfoList(CourseWordInfo courseWordInfo)
    {
        return courseWordInfoMapper.selectCourseWordInfoList(courseWordInfo);
    }

    /**
     * 新增课程作业
     *
     * @param courseWordInfo 课程作业
     * @return 结果
     */
    @Override
    public int insertCourseWordInfo(CourseWordInfo courseWordInfo)
    {
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
    public int updateCourseWordInfo(CourseWordInfo courseWordInfo)
    {
        courseWordInfo.setUpdateTime(DateUtils.getNowDate());
        return courseWordInfoMapper.updateCourseWordInfo(courseWordInfo);
    }

    /**
     * 批量删除课程作业
     *
     * @param ids 需要删除的课程作业主键
     * @return 结果
     */
    @Override
    public int deleteCourseWordInfoByIds(Long[] ids)
    {
        return courseWordInfoMapper.deleteCourseWordInfoByIds(ids);
    }

    /**
     * 删除课程作业信息
     *
     * @param id 课程作业主键
     * @return 结果
     */
    @Override
    public int deleteCourseWordInfoById(Long id)
    {
        return courseWordInfoMapper.deleteCourseWordInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<CourseWordInfo> getQueryWrapper(CourseWordInfoQuery courseWordInfoQuery){
        QueryWrapper<CourseWordInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = courseWordInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = courseWordInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long courseId = courseWordInfoQuery.getCourseId();
        queryWrapper.eq( StringUtils.isNotNull(courseId),"course_id",courseId);

        String wordName = courseWordInfoQuery.getWordName();
        queryWrapper.like(StringUtils.isNotEmpty(wordName) ,"word_name",wordName);

        String status = courseWordInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Long teacherId = courseWordInfoQuery.getTeacherId();
        queryWrapper.eq( StringUtils.isNotNull(teacherId),"teacher_id",teacherId);

        Date createTime = courseWordInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

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
