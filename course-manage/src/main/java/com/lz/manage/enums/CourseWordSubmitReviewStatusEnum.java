package com.lz.manage.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 课程作业提交审批状态枚举
 *
 * @author admin
 * @date 2026-05-26
 */
@Getter
public enum CourseWordSubmitReviewStatusEnum {

    /**
     * 待审批
     */
    COURSE_WORD_SUBMIT_REVIEW_STATUS_0("0", "待审批"),

    /**
     * 已审批
     */
    COURSE_WORD_SUBMIT_REVIEW_STATUS_1("1", "已审批");

    private final String value;
    private final String label;

    CourseWordSubmitReviewStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, CourseWordSubmitReviewStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (CourseWordSubmitReviewStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<CourseWordSubmitReviewStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
