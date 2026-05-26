package com.lz.manage.model.vo.courseWordInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.CourseWordInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程作业Vo对象 tb_course_word_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseWordInfoVo implements Serializable {
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
     * 作业名称
     */
    private String wordName;

    /**
     * 状态
     */
    private String status;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /**
     * 作业内容
     */
    private String workContent;

    /**
     * 作业文件
     */
    private String workFile;

    /**
     * 老师
     */
    private Long teacherId;
    private String teacherName;

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
     * @param courseWordInfo CourseWordInfo实体对象
     * @return CourseWordInfoVo
     */
    public static CourseWordInfoVo objToVo(CourseWordInfo courseWordInfo) {
        if (courseWordInfo == null) {
            return null;
        }
        CourseWordInfoVo courseWordInfoVo = new CourseWordInfoVo();
        BeanUtils.copyProperties(courseWordInfo, courseWordInfoVo);
        return courseWordInfoVo;
    }
}
