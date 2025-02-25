package com.practice.QLTV.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageResponse<T> {
    @Builder.Default
    int currentPage = 1;

    @Builder.Default
    int pageSize = 10;

    @Builder.Default
    int totalPages = 0;

    @Builder.Default
    long totalElements = 0;

    @Builder.Default
    List<T> data = Collections.emptyList();

    public static <T> PageResponse<T> from(Page<T> page) {
        return PageResponse.<T>builder()
                .currentPage(page.getNumber() + 1)
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .data(page.getContent())
                .build();
    }
}