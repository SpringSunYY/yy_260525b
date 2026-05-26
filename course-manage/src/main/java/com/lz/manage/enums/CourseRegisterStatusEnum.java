package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 课程报名状态枚举
 *
 * @author admin
 * @date 2026-05-26
 */
@Getter
public enum CourseRegisterStatusEnum {

    /**
     * 正常
     */
    COURSE_REGISTER_STATUS_0("0", "正常"),

    /**
     * 退课
     */
    COURSE_REGISTER_STATUS_1("1", "退课");

    private final String value;
    private final String label;

    CourseRegisterStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, CourseRegisterStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (CourseRegisterStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<CourseRegisterStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
