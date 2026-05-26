package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.CourseMaterialInfo;
import com.lz.manage.model.vo.courseMaterialInfo.CourseMaterialInfoVo;
import com.lz.manage.model.dto.courseMaterialInfo.CourseMaterialInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 课程资料Service接口
 * 
 * @author YY
 * @date 2026-05-26
 */
public interface ICourseMaterialInfoService extends IService<CourseMaterialInfo>
{
    //region mybatis代码
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
     * 批量删除课程资料
     * 
     * @param ids 需要删除的课程资料主键集合
     * @return 结果
     */
    public int deleteCourseMaterialInfoByIds(Long[] ids);

    /**
     * 删除课程资料信息
     * 
     * @param id 课程资料主键
     * @return 结果
     */
    public int deleteCourseMaterialInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param courseMaterialInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<CourseMaterialInfo> getQueryWrapper(CourseMaterialInfoQuery courseMaterialInfoQuery);

    /**
     * 转换vo
     *
     * @param courseMaterialInfoList CourseMaterialInfo集合
     * @return CourseMaterialInfoVO集合
     */
    List<CourseMaterialInfoVo> convertVoList(List<CourseMaterialInfo> courseMaterialInfoList);
}
