package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.CourseInfo;
import com.lz.manage.model.vo.courseInfo.CourseInfoVo;
import com.lz.manage.model.dto.courseInfo.CourseInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 课程信息Service接口
 *
 * @author YY
 * @date 2026-05-26
 */
public interface ICourseInfoService extends IService<CourseInfo>
{
    //region mybatis代码
    /**
     * 查询课程信息
     *
     * @param id 课程信息主键
     * @return 课程信息
     */
    public CourseInfo selectCourseInfoById(Long id);

    /**
     * 查询课程信息列表
     *
     * @param courseInfo 课程信息
     * @return 课程信息集合
     */
    public List<CourseInfo> selectCourseInfoList(CourseInfo courseInfo);

    List<CourseInfo> selectCourseInfoListHome(CourseInfo courseInfo);

    /**
     * 新增课程信息
     *
     * @param courseInfo 课程信息
     * @return 结果
     */
    public int insertCourseInfo(CourseInfo courseInfo);

    /**
     * 修改课程信息
     *
     * @param courseInfo 课程信息
     * @return 结果
     */
    public int updateCourseInfo(CourseInfo courseInfo);

    /**
     * 批量删除课程信息
     *
     * @param ids 需要删除的课程信息主键集合
     * @return 结果
     */
    public int deleteCourseInfoByIds(Long[] ids);

    /**
     * 删除课程信息信息
     *
     * @param id 课程信息主键
     * @return 结果
     */
    public int deleteCourseInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param courseInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<CourseInfo> getQueryWrapper(CourseInfoQuery courseInfoQuery);

    /**
     * 转换vo
     *
     * @param courseInfoList CourseInfo集合
     * @return CourseInfoVO集合
     */
    List<CourseInfoVo> convertVoList(List<CourseInfo> courseInfoList);

}
