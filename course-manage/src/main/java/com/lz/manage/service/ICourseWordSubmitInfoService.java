package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.CourseWordSubmitInfo;
import com.lz.manage.model.vo.courseWordSubmitInfo.CourseWordSubmitInfoVo;
import com.lz.manage.model.dto.courseWordSubmitInfo.CourseWordSubmitInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 作业提交Service接口
 * 
 * @author YY
 * @date 2026-05-26
 */
public interface ICourseWordSubmitInfoService extends IService<CourseWordSubmitInfo>
{
    //region mybatis代码
    /**
     * 查询作业提交
     * 
     * @param id 作业提交主键
     * @return 作业提交
     */
    public CourseWordSubmitInfo selectCourseWordSubmitInfoById(Long id);

    /**
     * 查询作业提交列表
     * 
     * @param courseWordSubmitInfo 作业提交
     * @return 作业提交集合
     */
    public List<CourseWordSubmitInfo> selectCourseWordSubmitInfoList(CourseWordSubmitInfo courseWordSubmitInfo);

    /**
     * 新增作业提交
     * 
     * @param courseWordSubmitInfo 作业提交
     * @return 结果
     */
    public int insertCourseWordSubmitInfo(CourseWordSubmitInfo courseWordSubmitInfo);

    /**
     * 修改作业提交
     * 
     * @param courseWordSubmitInfo 作业提交
     * @return 结果
     */
    public int updateCourseWordSubmitInfo(CourseWordSubmitInfo courseWordSubmitInfo);

    /**
     * 批量删除作业提交
     * 
     * @param ids 需要删除的作业提交主键集合
     * @return 结果
     */
    public int deleteCourseWordSubmitInfoByIds(Long[] ids);

    /**
     * 删除作业提交信息
     * 
     * @param id 作业提交主键
     * @return 结果
     */
    public int deleteCourseWordSubmitInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param courseWordSubmitInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<CourseWordSubmitInfo> getQueryWrapper(CourseWordSubmitInfoQuery courseWordSubmitInfoQuery);

    /**
     * 转换vo
     *
     * @param courseWordSubmitInfoList CourseWordSubmitInfo集合
     * @return CourseWordSubmitInfoVO集合
     */
    List<CourseWordSubmitInfoVo> convertVoList(List<CourseWordSubmitInfo> courseWordSubmitInfoList);
}
