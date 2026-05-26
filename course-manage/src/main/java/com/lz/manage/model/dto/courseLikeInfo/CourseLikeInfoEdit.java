package com.lz.manage.model.dto.courseLikeInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.CourseLikeInfo;
/**
 * 课程点赞Vo对象 tb_course_like_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseLikeInfoEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 课程 */
    private Long courseId;

    /** 老师 */
    private Long teacherId;

    /** 学生 */
    private Long userId;

    /**
     * 对象转封装类
     *
     * @param courseLikeInfoEdit 编辑对象
     * @return CourseLikeInfo
     */
    public static CourseLikeInfo editToObj(CourseLikeInfoEdit courseLikeInfoEdit) {
        if (courseLikeInfoEdit == null) {
            return null;
        }
        CourseLikeInfo courseLikeInfo = new CourseLikeInfo();
        BeanUtils.copyProperties(courseLikeInfoEdit, courseLikeInfo);
        return courseLikeInfo;
    }
}
