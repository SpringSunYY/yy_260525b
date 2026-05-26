package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.CourseLikeInfo;
import com.lz.manage.model.vo.courseLikeInfo.CourseLikeInfoVo;
import com.lz.manage.model.dto.courseLikeInfo.CourseLikeInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 课程点赞Service接口
 * 
 * @author YY
 * @date 2026-05-26
 */
public interface ICourseLikeInfoService extends IService<CourseLikeInfo>
{
    //region mybatis代码
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
     * 批量删除课程点赞
     * 
     * @param ids 需要删除的课程点赞主键集合
     * @return 结果
     */
    public int deleteCourseLikeInfoByIds(Long[] ids);

    /**
     * 删除课程点赞信息
     * 
     * @param id 课程点赞主键
     * @return 结果
     */
    public int deleteCourseLikeInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param courseLikeInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<CourseLikeInfo> getQueryWrapper(CourseLikeInfoQuery courseLikeInfoQuery);

    /**
     * 转换vo
     *
     * @param courseLikeInfoList CourseLikeInfo集合
     * @return CourseLikeInfoVO集合
     */
    List<CourseLikeInfoVo> convertVoList(List<CourseLikeInfo> courseLikeInfoList);
}
