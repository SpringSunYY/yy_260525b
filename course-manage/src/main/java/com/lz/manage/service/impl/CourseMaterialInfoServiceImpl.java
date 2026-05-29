package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.enums.CourseRegisterStatusEnum;
import com.lz.manage.mapper.CourseMaterialInfoMapper;
import com.lz.manage.model.domain.CourseInfo;
import com.lz.manage.model.domain.CourseMaterialInfo;
import com.lz.manage.model.domain.CourseRegisterInfo;
import com.lz.manage.model.dto.courseMaterialInfo.CourseMaterialInfoQuery;
import com.lz.manage.model.vo.courseMaterialInfo.CourseMaterialInfoVo;
import com.lz.manage.service.ICourseInfoService;
import com.lz.manage.service.ICourseMaterialInfoService;
import com.lz.manage.service.ICourseRegisterInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 课程资料Service业务层处理
 *
 * @author YY
 * @date 2026-05-26
 */
@Service
public class CourseMaterialInfoServiceImpl extends ServiceImpl<CourseMaterialInfoMapper, CourseMaterialInfo> implements ICourseMaterialInfoService {

    @Resource
    private CourseMaterialInfoMapper courseMaterialInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ICourseInfoService courseInfoService;

    @Resource
    private ICourseRegisterInfoService courseRegisterInfoService;
    //region mybatis代码

    /**
     * 查询课程资料
     *
     * @param id 课程资料主键
     * @return 课程资料
     */
    @Override
    public CourseMaterialInfo selectCourseMaterialInfoById(Long id) {
        return courseMaterialInfoMapper.selectCourseMaterialInfoById(id);
    }

    /**
     * 查询课程资料列表
     *
     * @param courseMaterialInfo 课程资料
     * @return 课程资料
     */
    @Override
    public List<CourseMaterialInfo> selectCourseMaterialInfoList(CourseMaterialInfo courseMaterialInfo) {
        //如果是老师只可以查看自己的作业
        //如果不是管理员且是老师
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())
                && SecurityUtils.hasRole("teacher")) {
            courseMaterialInfo.setUserId(SecurityUtils.getUserId());
        }
        //如果不是超级管理员是学生
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())
                && SecurityUtils.hasRole("student")) {
            //先查询到他注册的课程
            CourseRegisterInfo courseRegisterInfo = new CourseRegisterInfo();
            courseRegisterInfo.setStatus(CourseRegisterStatusEnum.COURSE_REGISTER_STATUS_0.getValue());
            courseRegisterInfo.setUserId(SecurityUtils.getUserId());
            List<CourseRegisterInfo> courseRegisterInfos = courseRegisterInfoService.selectCourseRegisterInfoList(courseRegisterInfo);
            List<Long> courseIds = courseRegisterInfos.stream().map(CourseRegisterInfo::getCourseId).toList();
            courseMaterialInfo.setCourseIds(courseIds);
        }
        List<CourseMaterialInfo> courseMaterialInfos = courseMaterialInfoMapper.selectCourseMaterialInfoList(courseMaterialInfo);
        for (CourseMaterialInfo info : courseMaterialInfos) {
            CourseInfo courseInfo = courseInfoService.selectCourseInfoById(info.getCourseId());
            if (StringUtils.isNotNull(courseInfo)) {
                info.setCourseName(courseInfo.getCourseName());
            }
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
        }
        return courseMaterialInfos;
    }

    @Override
    public List<CourseMaterialInfo> selectCourseMaterialInfoListHome(CourseMaterialInfo courseMaterialInfo) {
        List<CourseMaterialInfo> courseMaterialInfos = courseMaterialInfoMapper.selectCourseMaterialInfoList(courseMaterialInfo);
        for (CourseMaterialInfo info : courseMaterialInfos) {
            CourseInfo courseInfo = courseInfoService.selectCourseInfoById(info.getCourseId());
            if (StringUtils.isNotNull(courseInfo)) {
                info.setCourseName(courseInfo.getCourseName());
            }
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
        }
        return courseMaterialInfos;
    }

    /**
     * 新增课程资料
     *
     * @param courseMaterialInfo 课程资料
     * @return 结果
     */
    @Override
    public int insertCourseMaterialInfo(CourseMaterialInfo courseMaterialInfo) {
        //查询课程是否存在
        CourseInfo courseInfo = courseInfoService.selectCourseInfoById(courseMaterialInfo.getCourseId());
        ThrowUtils.throwIf(StringUtils.isNull(courseInfo), "课程不存在");
        courseMaterialInfo.setUserId(courseInfo.getUserId());

        courseMaterialInfo.setCreateBy(SecurityUtils.getUsername());
        courseMaterialInfo.setCreateTime(DateUtils.getNowDate());
        return courseMaterialInfoMapper.insertCourseMaterialInfo(courseMaterialInfo);
    }

    /**
     * 修改课程资料
     *
     * @param courseMaterialInfo 课程资料
     * @return 结果
     */
    @Override
    public int updateCourseMaterialInfo(CourseMaterialInfo courseMaterialInfo) {
        //查询课程是否存在
        CourseInfo courseInfo = courseInfoService.selectCourseInfoById(courseMaterialInfo.getCourseId());
        ThrowUtils.throwIf(StringUtils.isNull(courseInfo), "课程不存在");
        courseMaterialInfo.setUserId(courseInfo.getUserId());

        courseMaterialInfo.setUpdateBy(SecurityUtils.getUsername());
        courseMaterialInfo.setUpdateTime(DateUtils.getNowDate());
        return courseMaterialInfoMapper.updateCourseMaterialInfo(courseMaterialInfo);
    }

    /**
     * 批量删除课程资料
     *
     * @param ids 需要删除的课程资料主键
     * @return 结果
     */
    @Override
    public int deleteCourseMaterialInfoByIds(Long[] ids) {
        return courseMaterialInfoMapper.deleteCourseMaterialInfoByIds(ids);
    }

    /**
     * 删除课程资料信息
     *
     * @param id 课程资料主键
     * @return 结果
     */
    @Override
    public int deleteCourseMaterialInfoById(Long id) {
        return courseMaterialInfoMapper.deleteCourseMaterialInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<CourseMaterialInfo> getQueryWrapper(CourseMaterialInfoQuery courseMaterialInfoQuery) {
        QueryWrapper<CourseMaterialInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = courseMaterialInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = courseMaterialInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long courseId = courseMaterialInfoQuery.getCourseId();
        queryWrapper.eq(StringUtils.isNotNull(courseId), "course_id", courseId);

        String material = courseMaterialInfoQuery.getMaterial();
        queryWrapper.eq(StringUtils.isNotEmpty(material), "material", material);

        String materialCover = courseMaterialInfoQuery.getMaterialCover();
        queryWrapper.eq(StringUtils.isNotEmpty(materialCover), "material_cover", materialCover);

        Date createTime = courseMaterialInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CourseMaterialInfoVo> convertVoList(List<CourseMaterialInfo> courseMaterialInfoList) {
        if (StringUtils.isEmpty(courseMaterialInfoList)) {
            return Collections.emptyList();
        }
        return courseMaterialInfoList.stream().map(CourseMaterialInfoVo::objToVo).collect(Collectors.toList());
    }
}
