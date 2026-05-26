package com.lz.manage.model.dto.courseMaterialInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.CourseMaterialInfo;
/**
 * 课程资料Vo对象 tb_course_material_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseMaterialInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 课程 */
    private Long courseId;

    /** 资料名称 */
    private String material;

    /** 封面 */
    private String materialCover;

    /** 课程描述 */
    private String materialDesc;

    /** 课程文件 */
    private String materialFile;

    /** 老师 */
    private Long userId;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param courseMaterialInfoInsert 插入对象
     * @return CourseMaterialInfoInsert
     */
    public static CourseMaterialInfo insertToObj(CourseMaterialInfoInsert courseMaterialInfoInsert) {
        if (courseMaterialInfoInsert == null) {
            return null;
        }
        CourseMaterialInfo courseMaterialInfo = new CourseMaterialInfo();
        BeanUtils.copyProperties(courseMaterialInfoInsert, courseMaterialInfo);
        return courseMaterialInfo;
    }
}
