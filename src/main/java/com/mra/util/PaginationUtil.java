package com.mra.util;

import com.mra.dto.PagedResponse;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Utility class for pagination operations
 */
public class PaginationUtil {

    /**
     * Convert Spring Page to custom PagedResponse
     */
    public static <T> PagedResponse<T> createPagedResponse(Page<T> page) {
        return PagedResponse.<T>builder()
                .content(page.getContent())
                .pagination(PagedResponse.PaginationMetadata.builder()
                        .currentPage(page.getNumber())
                        .pageSize(page.getSize())
                        .totalElements(page.getTotalElements())
                        .totalPages(page.getTotalPages())
                        .first(page.isFirst())
                        .last(page.isLast())
                        .hasNext(page.hasNext())
                        .hasPrevious(page.hasPrevious())
                        .numberOfElements(page.getNumberOfElements())
                        .empty(page.isEmpty())
                        .build())
                .build();
    }

    /**
     * Create PagedResponse from list with manual pagination info
     */
    public static <T> PagedResponse<T> createPagedResponse(
            List<T> content, 
            int currentPage, 
            int pageSize, 
            long totalElements) {
        
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);
        boolean isFirst = currentPage == 0;
        boolean isLast = currentPage >= totalPages - 1;
        boolean hasNext = currentPage < totalPages - 1;
        boolean hasPrevious = currentPage > 0;
        
        return PagedResponse.<T>builder()
                .content(content)
                .pagination(PagedResponse.PaginationMetadata.builder()
                        .currentPage(currentPage)
                        .pageSize(pageSize)
                        .totalElements(totalElements)
                        .totalPages(totalPages)
                        .first(isFirst)
                        .last(isLast)
                        .hasNext(hasNext)
                        .hasPrevious(hasPrevious)
                        .numberOfElements(content.size())
                        .empty(content.isEmpty())
                        .build())
                .build();
    }
}