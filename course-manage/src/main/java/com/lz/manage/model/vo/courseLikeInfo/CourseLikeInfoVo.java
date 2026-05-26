package com.lz.manage.model.vo.courseLikeInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.CourseLikeInfo;
/**
 * 课程点赞Vo对象 tb_course_like_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseLikeInfoVo implements Serializable
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

    /** 创建时间 */
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
