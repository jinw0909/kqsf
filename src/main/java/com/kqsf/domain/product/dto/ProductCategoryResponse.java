package com.kqsf.domain.product.dto;
import com.kqsf.domain.product.entity.ProductCategory;

public record ProductCategoryResponse(
        Long id,
        String code,
        String name,
        String nameEn,
        String description,
        String descriptionEn,
        String thumbnailUrl,
        Integer sortOrder,
        Boolean active
) {
    public static ProductCategoryResponse from(ProductCategory category) {
        return new ProductCategoryResponse(
                category.getId(),
                category.getCode(),
                category.getName(),
                category.getNameEn(),
                category.getDescription(),
                category.getDescriptionEn(),
                category.getThumbnailUrl(),
                category.getSortOrder(),
                category.getActive()
        );
    }
}