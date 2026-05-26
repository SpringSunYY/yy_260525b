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
import com.lz.manage.mapper.CourseLikeInfoMapper;
import com.lz.manage.model.domain.CourseLikeInfo;
import com.lz.manage.service.ICourseLikeInfoService;
import com.lz.manage.model.dto.courseLikeInfo.CourseLikeInfoQuery;
import com.lz.manage.model.vo.courseLikeInfo.CourseLikeInfoVo;

/**
 * 课程点赞Service业务层处理
 *
 * @author YY
 * @date 2026-05-26
 */
@Service
public class CourseLikeInfoServiceImpl extends ServiceImpl<CourseLikeInfoMapper, CourseLikeInfo> implements ICourseLikeInfoService
{

    @Resource
    private CourseLikeInfoMapper courseLikeInfoMapper;

    //region mybatis代码
    /**
     * 查询课程点赞
     *
     * @param id 课程点赞主键
     * @return 课程点赞
     */
    @Override
    public CourseLikeInfo selectCourseLikeInfoById(Long id)
    {
        return courseLikeInfoMapper.selectCourseLikeInfoById(id);
    }

    /**
     * 查询课程点赞列表
     *
     * @param courseLikeInfo 课程点赞
     * @return 课程点赞
     */
    @Override
    public List<CourseLikeInfo> selectCourseLikeInfoList(CourseLikeInfo courseLikeInfo)
    {
        return courseLikeInfoMapper.selectCourseLikeInfoList(courseLikeInfo);
    }

    /**
     * 新增课程点赞
     *
     * @param courseLikeInfo 课程点赞
     * @return 结果
     */
    @Override
    public int insertCourseLikeInfo(CourseLikeInfo courseLikeInfo)
    {
        courseLikeInfo.setCreateTime(DateUtils.getNowDate());
        return courseLikeInfoMapper.insertCourseLikeInfo(courseLikeInfo);
    }

    /**
     * 修改课程点赞
     *
     * @param courseLikeInfo 课程点赞
     * @return 结果
     */
    @Override
    public int updateCourseLikeInfo(CourseLikeInfo courseLikeInfo)
    {
        return courseLikeInfoMapper.updateCourseLikeInfo(courseLikeInfo);
    }

    /**
     * 批量删除课程点赞
     *
     * @param ids 需要删除的课程点赞主键
     * @return 结果
     */
    @Override
    public int deleteCourseLikeInfoByIds(Long[] ids)
    {
        return courseLikeInfoMapper.deleteCourseLikeInfoByIds(ids);
    }

    /**
     * 删除课程点赞信息
     *
     * @param id 课程点赞主键
     * @return 结果
     */
    @Override
    public int deleteCourseLikeInfoById(Long id)
    {
        return courseLikeInfoMapper.deleteCourseLikeInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<CourseLikeInfo> getQueryWrapper(CourseLikeInfoQuery courseLikeInfoQuery){
        QueryWrapper<CourseLikeInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = courseLikeInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = courseLikeInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long courseId = courseLikeInfoQuery.getCourseId();
        queryWrapper.eq( StringUtils.isNotNull(courseId),"course_id",courseId);

        Long teacherId = courseLikeInfoQuery.getTeacherId();
        queryWrapper.eq( StringUtils.isNotNull(teacherId),"teacher_id",teacherId);

        Long userId = courseLikeInfoQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Date createTime = courseLikeInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CourseLikeInfoVo> convertVoList(List<CourseLikeInfo> courseLikeInfoList) {
        if (StringUtils.isEmpty(courseLikeInfoList)) {
            return Collections.emptyList();
        }
        return courseLikeInfoList.stream().map(CourseLikeInfoVo::objToVo).collect(Collectors.toList());
    }
}
