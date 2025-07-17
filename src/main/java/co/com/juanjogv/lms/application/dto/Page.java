package co.com.juanjogv.lms.application.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record Page<T>(
        List<T> content,
        long totalElements,
        long totalPages,
        boolean hasPrevious,
        boolean hasNext
) {}
