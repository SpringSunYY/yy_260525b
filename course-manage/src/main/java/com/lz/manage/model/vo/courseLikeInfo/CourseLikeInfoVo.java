package com.lz.manage.model.vo.courseLikeInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.CourseLikeInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程点赞Vo对象 tb_course_like_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseLikeInfoVo implements Serializable {
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
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;


    /**
     * 对象转封装类
     *
     * @param courseLikeInfo CourseLikeInfo实体对象
     * @return CourseLikeInfoVo
     */
    public static CourseLikeInfoVo objToVo(CourseLikeInfo courseLikeInfo) {
        if (courseLikeInfo == null) {
            return null;
        }
        CourseLikeInfoVo courseLikeInfoVo = new CourseLikeInfoVo();
        BeanUtils.copyProperties(courseLikeInfo, courseLikeInfoVo);
        return courseLikeInfoVo;
    }
}
