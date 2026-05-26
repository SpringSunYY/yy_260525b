package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 课程作业状态枚举
 *
 * @author admin
 * @date 2026-05-26
 */
@Getter
public enum CourseWorkStatusEnum {

    /**
     * 进行中
     */
    COURSE_WORK_STATUS_0("0", "进行中"),

    /**
     * 已结束
     */
    COURSE_WORK_STATUS_1("1", "已结束");

    private final String value;
    private final String label;

    CourseWorkStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, CourseWorkStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (CourseWorkStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<CourseWorkStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
