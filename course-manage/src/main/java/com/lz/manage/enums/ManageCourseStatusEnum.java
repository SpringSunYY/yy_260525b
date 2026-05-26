package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 课程状态枚举
 *
 * @author admin
 * @date 2026-05-26
 */
@Getter
public enum ManageCourseStatusEnum {

    /**
     * 关闭
     */
    MANAGE_COURSE_STATUS_0("0", "关闭"),

    /**
     * 开启
     */
    MANAGE_COURSE_STATUS_1("1", "开启");

    private final String value;
    private final String label;

    ManageCourseStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, ManageCourseStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (ManageCourseStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<ManageCourseStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
