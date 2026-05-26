package com.lz.manage.model.dto.courseWordSubmitInfo;

import java.util.Map;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.CourseWordSubmitInfo;
/**
 * 作业提交Query对象 tb_course_word_submit_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseWordSubmitInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 课程 */
    private Long courseId;

    /** 作业 */
    private Long wordId;

    /** 状态 */
    private String status;

    /** 批阅状态 */
    private String reviewStatus;

    /** 老师 */
    private Long teacherId;

    /** 学生 */
    private Long userId;

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
     * @param courseWordSubmitInfoQuery 查询对象
     * @return CourseWordSubmitInfo
     */
    public static CourseWordSubmitInfo queryToObj(CourseWordSubmitInfoQuery courseWordSubmitInfoQuery) {
        if (courseWordSubmitInfoQuery == null) {
            return null;
        }
        CourseWordSubmitInfo courseWordSubmitInfo = new CourseWordSubmitInfo();
        BeanUtils.copyProperties(courseWordSubmitInfoQuery, courseWordSubmitInfo);
        return courseWordSubmitInfo;
    }
}
