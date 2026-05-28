package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.annotation.CustomSort;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.CourseInfoMapper;
import com.lz.manage.model.domain.CourseInfo;
import com.lz.manage.model.domain.CourseLikeInfo;
import com.lz.manage.model.domain.CourseRegisterInfo;
import com.lz.manage.model.dto.courseInfo.CourseInfoQuery;
import com.lz.manage.model.vo.courseInfo.CourseInfoVo;
import com.lz.manage.service.ICourseInfoService;
import com.lz.manage.service.ICourseLikeInfoService;
import com.lz.manage.service.ICourseRegisterInfoService;
import com.lz.system.service.ISysUserService;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 课程信息Service业务层处理
 *
 * @author YY
 * @date 2026-05-26
 */
@Service
public class CourseInfoServiceImpl extends ServiceImpl<CourseInfoMapper, CourseInfo> implements ICourseInfoService {

    @Resource
    private CourseInfoMapper courseInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    @Lazy
    private ICourseLikeInfoService courseLikeInfoService;

    @Resource
    @Lazy
    private ICourseRegisterInfoService courseRegisterInfoService;
    //region mybatis代码

    /**
     * 查询课程信息
     *
     * @param id 课程信息主键
     * @return 课程信息
     */
    @Override
    public CourseInfo selectCourseInfoById(Long id) {
        CourseInfo courseInfo = courseInfoMapper.selectCourseInfoById(id);
        ThrowUtils.throwIf(StringUtils.isNull(courseInfo), "数据不存在");
        //查询老师
        SysUser sysUser = sysUserService.selectUserById(courseInfo.getUserId());
        if (StringUtils.isNotNull(sysUser)) {
            courseInfo.setUserName(sysUser.getUserName());
        }
        //查询是否点赞
        long count = courseLikeInfoService.count(new LambdaQueryWrapper<CourseLikeInfo>()
                .eq(CourseLikeInfo::getCourseId, courseInfo.getId())
                .eq(CourseLikeInfo::getUserId, SecurityUtils.getUserId()));
        courseInfo.setLike(count > 0);
        //查询是否注册
        long registerCount = courseRegisterInfoService.count(new LambdaQueryWrapper<CourseRegisterInfo>()
                .eq(CourseRegisterInfo::getCourseId, courseInfo.getId())
                .eq(CourseRegisterInfo::getUserId, SecurityUtils.getUserId()));
        courseInfo.setRegister(registerCount > 0);
        return courseInfo;
    }

    /**
     * 查询课程信息列表
     *
     * @param courseInfo 课程信息
     * @return 课程信息
     */
    @Override
    @CustomSort(sortFields =
            {"createTime", "orderNum", "registerNum", "likeNum"},
            sortMappingFields =
                    {"create_time", "order_num", "register_num", "like_num"})
    public List<CourseInfo> selectCourseInfoList(CourseInfo courseInfo) {
        List<CourseInfo> courseInfos = courseInfoMapper.selectCourseInfoList(courseInfo);
        for (CourseInfo info : courseInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
        }
        return courseInfos;
    }

    /**
     * 新增课程信息
     *
     * @param courseInfo 课程信息
     * @return 结果
     */
    @Override
    public int insertCourseInfo(CourseInfo courseInfo) {
        //查询用户是否存在
        SysUser sysUser = sysUserService.selectUserById(courseInfo.getUserId());
        ThrowUtils.throwIf(StringUtils.isNull(sysUser), "老师不存在");

        courseInfo.setLikeNum(0L);
        courseInfo.setRegisterNum(0L);
        courseInfo.setCreateBy(SecurityUtils.getUsername());
        courseInfo.setCreateTime(DateUtils.getNowDate());
        return courseInfoMapper.insertCourseInfo(courseInfo);
    }

    /**
     * 修改课程信息
     *
     * @param courseInfo 课程信息
     * @return 结果
     */
    @Override
    public int updateCourseInfo(CourseInfo courseInfo) {
        //查询用户是否存在
        SysUser sysUser = sysUserService.selectUserById(courseInfo.getUserId());
        ThrowUtils.throwIf(StringUtils.isNull(sysUser), "老师不存在");
        courseInfo.setUpdateBy(SecurityUtils.getUsername());
        courseInfo.setUpdateTime(DateUtils.getNowDate());
        return courseInfoMapper.updateCourseInfo(courseInfo);
    }

    /**
     * 批量删除课程信息
     *
     * @param ids 需要删除的课程信息主键
     * @return 结果
     */
    @Override
    public int deleteCourseInfoByIds(Long[] ids) {
        return courseInfoMapper.deleteCourseInfoByIds(ids);
    }

    /**
     * 删除课程信息信息
     *
     * @param id 课程信息主键
     * @return 结果
     */
    @Override
    public int deleteCourseInfoById(Long id) {
        return courseInfoMapper.deleteCourseInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<CourseInfo> getQueryWrapper(CourseInfoQuery courseInfoQuery) {
        QueryWrapper<CourseInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = courseInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = courseInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String courseType = courseInfoQuery.getCourseType();
        queryWrapper.eq(StringUtils.isNotEmpty(courseType), "course_type", courseType);

        String courseName = courseInfoQuery.getCourseName();
        queryWrapper.like(StringUtils.isNotEmpty(courseName), "course_name", courseName);

        String status = courseInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Long userId = courseInfoQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        Date createTime = courseInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CourseInfoVo> convertVoList(List<CourseInfo> courseInfoList) {
        if (StringUtils.isEmpty(courseInfoList)) {
            return Collections.emptyList();
        }
        return courseInfoList.stream().map(CourseInfoVo::objToVo).collect(Collectors.toList());
    }
}
