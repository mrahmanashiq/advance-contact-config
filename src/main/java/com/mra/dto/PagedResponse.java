package com.mra.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Paginated response wrapper with comprehensive pagination metadata
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Paginated response with metadata")
public class PagedResponse<T> {

    @Schema(description = "List of items for current page")
    private List<T> content;

    @Schema(description = "Pagination metadata")
    private PaginationMetadata pagination;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Pagination metadata")
    public static class PaginationMetadata {
        
        @Schema(description = "Current page number (0-based)", example = "0")
        private int currentPage;
        
        @Schema(description = "Number of items per page", example = "25")
        private int pageSize;
        
        @Schema(description = "Total number of items", example = "150")
        private long totalElements;
        
        @Schema(description = "Total number of pages", example = "6")
        private int totalPages;
        
        @Schema(description = "Whether this is the first page", example = "true")
        private boolean first;
        
        @Schema(description = "Whether this is the last page", example = "false")
        private boolean last;
        
        @Schema(description = "Whether there is a next page", example = "true")
        private boolean hasNext;
        
        @Schema(description = "Whether there is a previous page", example = "false")
        private boolean hasPrevious;
        
        @Schema(description = "Number of items in current page", example = "25")
        private int numberOfElements;
        
        @Schema(description = "Whether the page is empty", example = "false")
        private boolean empty;
    }
}