package com.lz.manage.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 课程注册对象 tb_course_register_info
 *
 * @author YY
 * @date 2026-05-26
 */
@TableName("tb_course_register_info")
@Data
public class CourseRegisterInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 课程
     */
    @Excel(name = "课程", type = Excel.Type.IMPORT)
    private Long courseId;
    @TableField(exist = false)
    @Excel(name = "课程", type = Excel.Type.EXPORT)
    private String courseName;

    /**
     * 状态
     */
    @Excel(name = "状态", dictType = "course_register_status")
    private String status;

    /**
     * 老师
     */
    @Excel(name = "老师", type = Excel.Type.IMPORT)
    private Long teacherId;
    @TableField(exist = false)
    @Excel(name = "老师", type = Excel.Type.EXPORT)
    private String teacherName;

    /**
     * 学生
     */
    @Excel(name = "学生", type = Excel.Type.IMPORT)
    private Long userId;
    @TableField(exist = false)
    @Excel(name = "学生", type = Excel.Type.EXPORT)
    private String userName;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /**
     * 创建人
     */
    @Excel(name = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
