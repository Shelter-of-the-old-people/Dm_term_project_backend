package com.github.term_project.global.common;

import java.util.List;

public record PageResponse<T>(
        List<T> items,
        int page,
        int size,
        long totalItems,
        int totalPages,
        boolean hasNext) {

    public static <T> PageResponse<T> of(List<T> items, int page, int size, long totalItems) {
        int totalPages = size == 0 ? 0 : (int) Math.ceil((double) totalItems / size);
        boolean hasNext = page < totalPages;
        return new PageResponse<>(items, page, size, totalItems, totalPages, hasNext);
    }
}
