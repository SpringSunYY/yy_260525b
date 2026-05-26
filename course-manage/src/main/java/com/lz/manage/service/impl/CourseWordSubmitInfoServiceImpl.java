package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import com.lz.common.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.CourseWordSubmitInfoMapper;
import com.lz.manage.model.domain.CourseWordSubmitInfo;
import com.lz.manage.service.ICourseWordSubmitInfoService;
import com.lz.manage.model.dto.courseWordSubmitInfo.CourseWordSubmitInfoQuery;
import com.lz.manage.model.vo.courseWordSubmitInfo.CourseWordSubmitInfoVo;

/**
 * 作业提交Service业务层处理
 *
 * @author YY
 * @date 2026-05-26
 */
@Service
public class CourseWordSubmitInfoServiceImpl extends ServiceImpl<CourseWordSubmitInfoMapper, CourseWordSubmitInfo> implements ICourseWordSubmitInfoService
{

    @Resource
    private CourseWordSubmitInfoMapper courseWordSubmitInfoMapper;

    //region mybatis代码
    /**
     * 查询作业提交
     *
     * @param id 作业提交主键
     * @return 作业提交
     */
    @Override
    public CourseWordSubmitInfo selectCourseWordSubmitInfoById(Long id)
    {
        return courseWordSubmitInfoMapper.selectCourseWordSubmitInfoById(id);
    }

    /**
     * 查询作业提交列表
     *
     * @param courseWordSubmitInfo 作业提交
     * @return 作业提交
     */
    @Override
    public List<CourseWordSubmitInfo> selectCourseWordSubmitInfoList(CourseWordSubmitInfo courseWordSubmitInfo)
    {
        return courseWordSubmitInfoMapper.selectCourseWordSubmitInfoList(courseWordSubmitInfo);
    }

    /**
     * 新增作业提交
     *
     * @param courseWordSubmitInfo 作业提交
     * @return 结果
     */
    @Override
    public int insertCourseWordSubmitInfo(CourseWordSubmitInfo courseWordSubmitInfo)
    {
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
    public int updateCourseWordSubmitInfo(CourseWordSubmitInfo courseWordSubmitInfo)
    {
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
    public int deleteCourseWordSubmitInfoByIds(Long[] ids)
    {
        return courseWordSubmitInfoMapper.deleteCourseWordSubmitInfoByIds(ids);
    }

    /**
     * 删除作业提交信息
     *
     * @param id 作业提交主键
     * @return 结果
     */
    @Override
    public int deleteCourseWordSubmitInfoById(Long id)
    {
        return courseWordSubmitInfoMapper.deleteCourseWordSubmitInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<CourseWordSubmitInfo> getQueryWrapper(CourseWordSubmitInfoQuery courseWordSubmitInfoQuery){
        QueryWrapper<CourseWordSubmitInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = courseWordSubmitInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = courseWordSubmitInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long courseId = courseWordSubmitInfoQuery.getCourseId();
        queryWrapper.eq( StringUtils.isNotNull(courseId),"course_id",courseId);

        Long wordId = courseWordSubmitInfoQuery.getWordId();
        queryWrapper.eq( StringUtils.isNotNull(wordId),"word_id",wordId);

        String status = courseWordSubmitInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        String reviewStatus = courseWordSubmitInfoQuery.getReviewStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(reviewStatus) ,"review_status",reviewStatus);

        Long teacherId = courseWordSubmitInfoQuery.getTeacherId();
        queryWrapper.eq( StringUtils.isNotNull(teacherId),"teacher_id",teacherId);

        Long userId = courseWordSubmitInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = courseWordSubmitInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CourseWordSubmitInfoVo> convertVoList(List<CourseWordSubmitInfo> courseWordSubmitInfoList) {
        if (StringUtils.isEmpty(courseWordSubmitInfoList)) {
            return Collections.emptyList();
        }
        return courseWordSubmitInfoList.stream().map(CourseWordSubmitInfoVo::objToVo).collect(Collectors.toList());
    }
}
