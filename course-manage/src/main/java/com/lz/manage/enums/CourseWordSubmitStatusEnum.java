package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 课程作业提交状态枚举
 *
 * @author admin
 * @date 2026-05-26
 */
@Getter
public enum CourseWordSubmitStatusEnum {

    /**
     * 待提交
     */
    COURSE_WORD_SUBMIT_STATUS_0("0", "待提交"),

    /**
     * 已提交
     */
    COURSE_WORD_SUBMIT_STATUS_1("1", "已提交");

    private final String value;
    private final String label;

    CourseWordSubmitStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, CourseWordSubmitStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (CourseWordSubmitStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<CourseWordSubmitStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
