package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.CourseRegisterInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 课程注册Mapper接口
 * 
 * @author YY
 * @date 2026-05-26
 */
public interface CourseRegisterInfoMapper extends BaseMapper<CourseRegisterInfo>
{
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
     * 删除课程注册
     * 
     * @param id 课程注册主键
     * @return 结果
     */
    public int deleteCourseRegisterInfoById(Long id);

    /**
     * 批量删除课程注册
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseRegisterInfoByIds(Long[] ids);
}
