package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.CourseLikeInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 课程点赞Mapper接口
 * 
 * @author YY
 * @date 2026-05-26
 */
public interface CourseLikeInfoMapper extends BaseMapper<CourseLikeInfo>
{
    /**
     * 查询课程点赞
     * 
     * @param id 课程点赞主键
     * @return 课程点赞
     */
    public CourseLikeInfo selectCourseLikeInfoById(Long id);

    /**
     * 查询课程点赞列表
     * 
     * @param courseLikeInfo 课程点赞
     * @return 课程点赞集合
     */
    public List<CourseLikeInfo> selectCourseLikeInfoList(CourseLikeInfo courseLikeInfo);

    /**
     * 新增课程点赞
     * 
     * @param courseLikeInfo 课程点赞
     * @return 结果
     */
    public int insertCourseLikeInfo(CourseLikeInfo courseLikeInfo);

    /**
     * 修改课程点赞
     * 
     * @param courseLikeInfo 课程点赞
     * @return 结果
     */
    public int updateCourseLikeInfo(CourseLikeInfo courseLikeInfo);

    /**
     * 删除课程点赞
     * 
     * @param id 课程点赞主键
     * @return 结果
     */
    public int deleteCourseLikeInfoById(Long id);

    /**
     * 批量删除课程点赞
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseLikeInfoByIds(Long[] ids);
}
