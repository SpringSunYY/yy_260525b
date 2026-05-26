package com.lz.manage.model.dto.courseWordInfo;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.CourseWordInfo;
/**
 * 课程作业Query对象 tb_course_word_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseWordInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 课程 */
    private Long courseId;

    /** 作业名称 */
    private String wordName;

    /** 状态 */
    private String status;

    /** 老师 */
    private Long teacherId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param courseWordInfoQuery 查询对象
     * @return CourseWordInfo
     */
    public static CourseWordInfo queryToObj(CourseWordInfoQuery courseWordInfoQuery) {
        if (courseWordInfoQuery == null) {
            return null;
        }
        CourseWordInfo courseWordInfo = new CourseWordInfo();
        BeanUtils.copyProperties(courseWordInfoQuery, courseWordInfo);
        return courseWordInfo;
    }
}
