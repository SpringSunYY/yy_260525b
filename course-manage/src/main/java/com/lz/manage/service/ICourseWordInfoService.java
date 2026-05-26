package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.CourseWordInfo;
import com.lz.manage.model.vo.courseWordInfo.CourseWordInfoVo;
import com.lz.manage.model.dto.courseWordInfo.CourseWordInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 课程作业Service接口
 * 
 * @author YY
 * @date 2026-05-26
 */
public interface ICourseWordInfoService extends IService<CourseWordInfo>
{
    //region mybatis代码
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
     * 批量删除课程作业
     * 
     * @param ids 需要删除的课程作业主键集合
     * @return 结果
     */
    public int deleteCourseWordInfoByIds(Long[] ids);

    /**
     * 删除课程作业信息
     * 
     * @param id 课程作业主键
     * @return 结果
     */
    public int deleteCourseWordInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param courseWordInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<CourseWordInfo> getQueryWrapper(CourseWordInfoQuery courseWordInfoQuery);

    /**
     * 转换vo
     *
     * @param courseWordInfoList CourseWordInfo集合
     * @return CourseWordInfoVO集合
     */
    List<CourseWordInfoVo> convertVoList(List<CourseWordInfo> courseWordInfoList);
}
