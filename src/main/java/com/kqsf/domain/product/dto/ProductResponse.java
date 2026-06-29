package com.kqsf.domain.product.dto;

import com.kqsf.domain.product.entity.Product;
import com.kqsf.domain.product.entity.ProductProcessingOption;

import java.util.List;

public record ProductResponse(
        Long id,
        String code,

        Long categoryId,
        String categoryCode,
        String categoryName,

        String name,
        String nameEn,

        String shortDescription,
        String shortDescriptionEn,

        String description,
        String descriptionEn,

        String thumbnailUrl,
        String mainImageUrl,

        Boolean active,
        Boolean inquiryEnabled,
        Integer sortOrder,

        List<ProcessingOptionResponse> processingOptions
) {
    public static ProductResponse from(
            Product product,
            List<ProductProcessingOption> options
    ) {
        return new ProductResponse(
                product.getId(),
                product.getCode(),

                product.getCategory().getId(),
                product.getCategory().getCode(),
                product.getCategory().getName(),

                product.getName(),
                product.getNameEn(),

                product.getShortDescription(),
                product.getShortDescriptionEn(),

                product.getDescription(),
                product.getDescriptionEn(),

                product.getThumbnailUrl(),
                product.getMainImageUrl(),

                product.getActive(),
                product.getInquiryEnabled(),
                product.getSortOrder(),

                options.stream()
                        .map(ProcessingOptionResponse::from)
                        .toList()
        );
    }

    public record ProcessingOptionResponse(
            Long id,
            String title,
            String titleEn,
            String summary,
            String summaryEn,
            String description,
            String descriptionEn,
            String imageUrl,
            Boolean active,
            Integer sortOrder
    ) {
        public static ProcessingOptionResponse from(ProductProcessingOption option) {
            return new ProcessingOptionResponse(
                    option.getId(),
                    option.getTitle(),
                    option.getTitleEn(),
                    option.getSummary(),
                    option.getSummaryEn(),
                    option.getDescription(),
                    option.getDescriptionEn(),
                    option.getImageUrl(),
                    option.getActive(),
                    option.getSortOrder()
            );
        }
    }
}