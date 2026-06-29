package com.kqsf.domain.product.dto;

import com.kqsf.domain.product.entity.ProductProcessingOption;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductProcessingOptionForm {

    private String title;
    private String titleEn;

    private String summary;
    private String summaryEn;

    private String description;
    private String descriptionEn;

    private String imageUrl;

    private Boolean active = true;

    private Integer sortOrder;

    public static ProductProcessingOptionForm from(ProductProcessingOption option) {
        ProductProcessingOptionForm form = new ProductProcessingOptionForm();

        form.setTitle(option.getTitle());
        form.setTitleEn(option.getTitleEn());
        form.setSummary(option.getSummary());
        form.setSummaryEn(option.getSummaryEn());
        form.setDescription(option.getDescription());
        form.setDescriptionEn(option.getDescriptionEn());
        form.setImageUrl(option.getImageUrl());
        form.setActive(option.getActive());
        form.setSortOrder(option.getSortOrder());

        return form;
    }
}