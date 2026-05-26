package com.lz.manage.model.vo.courseMaterialInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.CourseMaterialInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程资料Vo对象 tb_course_material_info
 *
 * @author YY
 * @date 2026-05-26
 */
@Data
public class CourseMaterialInfoVo implements Serializable {
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
     * 资料名称
     */
    private String material;

    /**
     * 封面
     */
    private String materialCover;

    /**
     * 资料描述
     */
    private String materialDesc;

    /**
     * 资料文件
     */
    private String materialFile;

    /**
     * 老师
     */
    private Long userId;
    private String userName;
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
     * @param courseMaterialInfo CourseMaterialInfo实体对象
     * @return CourseMaterialInfoVo
     */
    public static CourseMaterialInfoVo objToVo(CourseMaterialInfo courseMaterialInfo) {
        if (courseMaterialInfo == null) {
            return null;
        }
        CourseMaterialInfoVo courseMaterialInfoVo = new CourseMaterialInfoVo();
        BeanUtils.copyProperties(courseMaterialInfo, courseMaterialInfoVo);
        return courseMaterialInfoVo;
    }
}
