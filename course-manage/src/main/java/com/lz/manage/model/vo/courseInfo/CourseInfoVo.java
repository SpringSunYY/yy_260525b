package com.lz.manage.model.vo.courseInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.CourseInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程信息Vo对象 tb_course_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 课程编号
     */
    private Long id;

    /**
     * 课程类型
     */
    private String courseType;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 封面
     */
    private String courseCover;

    /**
     * 课程描述
     */
    private String courseDesc;

    /**
     * 排序
     */
    private Long orderNum;

    /**
     * 注册人数
     */
    private Long registerNum;

    /**
     * 点赞人数
     */
    private Long likeNum;

    /**
     * 状态
     */
    private String status;

    /**
     * 老师
     */
    private Long userId;
    private String userName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 对象转封装类
     *
     * @param courseInfo CourseInfo实体对象
     * @return CourseInfoVo
     */
    public static CourseInfoVo objToVo(CourseInfo courseInfo) {
        if (courseInfo == null) {
            return null;
        }
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(courseInfo, courseInfoVo);
        return courseInfoVo;
    }
}
