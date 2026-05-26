package com.lz.manage.model.vo.courseRegisterInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.CourseRegisterInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程注册Vo对象 tb_course_register_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseRegisterInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 课程
     */
    private Long courseId;
    private String courseName;

    /**
     * 状态
     */
    private String status;

    /**
     * 老师
     */
    private Long teacherId;
    private String teacherName;

    /**
     * 学生
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
     * @param courseRegisterInfo CourseRegisterInfo实体对象
     * @return CourseRegisterInfoVo
     */
    public static CourseRegisterInfoVo objToVo(CourseRegisterInfo courseRegisterInfo) {
        if (courseRegisterInfo == null) {
            return null;
        }
        CourseRegisterInfoVo courseRegisterInfoVo = new CourseRegisterInfoVo();
        BeanUtils.copyProperties(courseRegisterInfo, courseRegisterInfoVo);
        return courseRegisterInfoVo;
    }
}
