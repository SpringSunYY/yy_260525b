package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.CourseWordSubmitInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 作业提交Mapper接口
 * 
 * @author YY
 * @date 2026-05-26
 */
public interface CourseWordSubmitInfoMapper extends BaseMapper<CourseWordSubmitInfo>
{
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
     * 删除作业提交
     * 
     * @param id 作业提交主键
     * @return 结果
     */
    public int deleteCourseWordSubmitInfoById(Long id);

    /**
     * 批量删除作业提交
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCourseWordSubmitInfoByIds(Long[] ids);
}
