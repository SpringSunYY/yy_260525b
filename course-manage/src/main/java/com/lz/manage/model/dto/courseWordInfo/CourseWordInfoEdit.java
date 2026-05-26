package com.lz.manage.model.dto.courseWordInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.CourseWordInfo;
/**
 * 课程作业Vo对象 tb_course_word_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseWordInfoEdit implements Serializable
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

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    /** 作业内容 */
    private String workContent;

    /** 作业文件 */
    private String workFile;

    /** 老师 */
    private Long teacherId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param courseWordInfoEdit 编辑对象
     * @return CourseWordInfo
     */
    public static CourseWordInfo editToObj(CourseWordInfoEdit courseWordInfoEdit) {
        if (courseWordInfoEdit == null) {
            return null;
        }
        CourseWordInfo courseWordInfo = new CourseWordInfo();
        BeanUtils.copyProperties(courseWordInfoEdit, courseWordInfo);
        return courseWordInfo;
    }
}
