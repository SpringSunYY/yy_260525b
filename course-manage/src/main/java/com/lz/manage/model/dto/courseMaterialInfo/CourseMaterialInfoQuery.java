package com.lz.manage.model.dto.courseMaterialInfo;

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
import com.lz.manage.model.domain.CourseMaterialInfo;
/**
 * 课程资料Query对象 tb_course_material_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseMaterialInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 课程 */
    private Long courseId;

    /** 资料名称 */
    private String material;

    /** 封面 */
    private String materialCover;

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
     * @param courseMaterialInfoQuery 查询对象
     * @return CourseMaterialInfo
     */
    public static CourseMaterialInfo queryToObj(CourseMaterialInfoQuery courseMaterialInfoQuery) {
        if (courseMaterialInfoQuery == null) {
            return null;
        }
        CourseMaterialInfo courseMaterialInfo = new CourseMaterialInfo();
        BeanUtils.copyProperties(courseMaterialInfoQuery, courseMaterialInfo);
        return courseMaterialInfo;
    }
}
