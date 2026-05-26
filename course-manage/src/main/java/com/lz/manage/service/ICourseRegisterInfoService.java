package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.CourseRegisterInfo;
import com.lz.manage.model.vo.courseRegisterInfo.CourseRegisterInfoVo;
import com.lz.manage.model.dto.courseRegisterInfo.CourseRegisterInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 课程注册Service接口
 * 
 * @author YY
 * @date 2026-05-26
 */
public interface ICourseRegisterInfoService extends IService<CourseRegisterInfo>
{
    //region mybatis代码
    /**
     * 查询课程注册
     * 
     * @param id 课程注册主键
     * @return 课程注册
     */
    public CourseRegisterInfo selectCourseRegisterInfoById(Long id);

    /**
     * 查询课程注册列表
     * 
     * @param courseRegisterInfo 课程注册
     * @return 课程注册集合
     */
    public List<CourseRegisterInfo> selectCourseRegisterInfoList(CourseRegisterInfo courseRegisterInfo);

    /**
     * 新增课程注册
     * 
     * @param courseRegisterInfo 课程注册
     * @return 结果
     */
    public int insertCourseRegisterInfo(CourseRegisterInfo courseRegisterInfo);

    /**
     * 修改课程注册
     * 
     * @param courseRegisterInfo 课程注册
     * @return 结果
     */
    public int updateCourseRegisterInfo(CourseRegisterInfo courseRegisterInfo);

    /**
     * 批量删除课程注册
     * 
     * @param ids 需要删除的课程注册主键集合
     * @return 结果
     */
    public int deleteCourseRegisterInfoByIds(Long[] ids);

    /**
     * 删除课程注册信息
     * 
     * @param id 课程注册主键
     * @return 结果
     */
    public int deleteCourseRegisterInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param courseRegisterInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<CourseRegisterInfo> getQueryWrapper(CourseRegisterInfoQuery courseRegisterInfoQuery);

    /**
     * 转换vo
     *
     * @param courseRegisterInfoList CourseRegisterInfo集合
     * @return CourseRegisterInfoVO集合
     */
    List<CourseRegisterInfoVo> convertVoList(List<CourseRegisterInfo> courseRegisterInfoList);
}
