package com.lz.manage.model.dto.courseRegisterInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.CourseRegisterInfo;
/**
 * 课程注册Vo对象 tb_course_register_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseRegisterInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 课程 */
    private Long courseId;

    /** 状态 */
    private String status;

    /** 老师 */
    private Long teacherId;

    /** 学生 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param courseRegisterInfoInsert 插入对象
     * @return CourseRegisterInfoInsert
     */
    public static CourseRegisterInfo insertToObj(CourseRegisterInfoInsert courseRegisterInfoInsert) {
        if (courseRegisterInfoInsert == null) {
            return null;
        }
        CourseRegisterInfo courseRegisterInfo = new CourseRegisterInfo();
        BeanUtils.copyProperties(courseRegisterInfoInsert, courseRegisterInfo);
        return courseRegisterInfo;
    }
}
