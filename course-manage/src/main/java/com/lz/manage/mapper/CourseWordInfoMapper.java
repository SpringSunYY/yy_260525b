package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.CourseWordInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 课程作业Mapper接口
 * 
 * @author YY
 * @date 2026-05-26
 */
public interface CourseWordInfoMapper extends BaseMapper<CourseWordInfo>
{
    /**
     * 查询课程作业
     * 
     * @param id 课程作业主键
     * @return 课程作业
     */
    public CourseWordInfo selectCourseWordInfoById(Long id);

    /**
     * 查询课程作业列表
     * 
     * @param courseWordInfo 课程作业
     * @return 课程作业集合
     */
    public List<CourseWordInfo> selectCourseWordInfoList(CourseWordInfo courseWordInfo);

    /**
     * 新增课程作业
     * 
     * @param courseWordInfo 课程作业
     * @return 结果
     */
    public int insertCourseWordInfo(CourseWordInfo courseWordInfo);

    /**
     * 修改课程作业
     * 
     * @param courseWordInfo 课程作业
     * @return 结果
     */
    public int updateCourseWordInfo(CourseWordInfo courseWordInfo);

    /**
     * 删除课程作业
     * 
     * @param id 课程作业主键
     * @return 结果
     */
    public int deleteCourseWordInfoById(Long id);

    /**
     * 批量删除课程作业
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseWordInfoByIds(Long[] ids);
}
