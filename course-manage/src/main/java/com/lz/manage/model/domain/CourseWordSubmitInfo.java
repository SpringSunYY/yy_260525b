package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 作业提交对象 tb_course_word_submit_info
 *
 * @author YY
 * @date 2026-05-26
 */
@TableName("tb_course_word_submit_info")
@Data
public class CourseWordSubmitInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 课程 */
    @Excel(name = "课程")
    private Long courseId;

    /** 作业 */
    @Excel(name = "作业")
    private Long wordId;

    /** 状态 */
    @Excel(name = "状态", dictType = "course_word_submit_status")
    private String status;

    /** 提交内容 */
    @Excel(name = "提交内容")
    private String submitContent;

    /** 提交文件 */
    @Excel(name = "提交文件")
    private String submitFile;

    /** 提交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "提交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date submitTime;

    /** 批阅状态 */
    @Excel(name = "批阅状态", dictType = "course_word_submit_review_status")
    private String reviewStatus;

    /** 分数 */
    @Excel(name = "分数")
    private BigDecimal score;

    /** 批阅时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "批阅时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewTime;

    /** 老师 */
    @Excel(name = "老师")
    private Long teacherId;

    /** 学生 */
    @Excel(name = "学生")
    private Long userId;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
