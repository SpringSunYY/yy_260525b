package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.CourseMaterialInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 课程资料Mapper接口
 * 
 * @author YY
 * @date 2026-05-26
 */
public interface CourseMaterialInfoMapper extends BaseMapper<CourseMaterialInfo>
{
    /**
     * 查询课程资料
     * 
     * @param id 课程资料主键
     * @return 课程资料
     */
    public CourseMaterialInfo selectCourseMaterialInfoById(Long id);

    /**
     * 查询课程资料列表
     * 
     * @param courseMaterialInfo 课程资料
     * @return 课程资料集合
     */
    public List<CourseMaterialInfo> selectCourseMaterialInfoList(CourseMaterialInfo courseMaterialInfo);

    /**
     * 新增课程资料
     * 
     * @param courseMaterialInfo 课程资料
     * @return 结果
     */
    public int insertCourseMaterialInfo(CourseMaterialInfo courseMaterialInfo);

    /**
     * 修改课程资料
     * 
     * @param courseMaterialInfo 课程资料
     * @return 结果
     */
    public int updateCourseMaterialInfo(CourseMaterialInfo courseMaterialInfo);

    /**
     * 删除课程资料
     * 
     * @param id 课程资料主键
     * @return 结果
     */
    public int deleteCourseMaterialInfoById(Long id);

    /**
     * 批量删除课程资料
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseMaterialInfoByIds(Long[] ids);
}
