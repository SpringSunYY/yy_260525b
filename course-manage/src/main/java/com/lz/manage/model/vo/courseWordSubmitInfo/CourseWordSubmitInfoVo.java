package com.lz.manage.model.vo.courseWordSubmitInfo;

import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.CourseWordSubmitInfo;
/**
 * 作业提交Vo对象 tb_course_word_submit_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseWordSubmitInfoVo implements Serializable
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

    /** 提交内容 */
    private String submitContent;

    /** 提交文件 */
    private String submitFile;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date submitTime;

    /** 批阅状态 */
    private String reviewStatus;

    /** 分数 */
    private BigDecimal score;

    /** 批阅时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date reviewTime;

    /** 老师 */
    private Long teacherId;

    /** 学生 */
    private Long userId;

    /** 备注 */
    private String remark;

    /** 创建人 */
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param courseWordSubmitInfo CourseWordSubmitInfo实体对象
     * @return CourseWordSubmitInfoVo
     */
    public static CourseWordSubmitInfoVo objToVo(CourseWordSubmitInfo courseWordSubmitInfo) {
        if (courseWordSubmitInfo == null) {
            return null;
        }
        CourseWordSubmitInfoVo courseWordSubmitInfoVo = new CourseWordSubmitInfoVo();
        BeanUtils.copyProperties(courseWordSubmitInfo, courseWordSubmitInfoVo);
        return courseWordSubmitInfoVo;
    }
}
