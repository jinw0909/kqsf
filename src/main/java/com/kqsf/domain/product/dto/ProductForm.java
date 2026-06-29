package com.kqsf.domain.product.dto;

import com.kqsf.domain.product.entity.Product;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {

    private Long categoryId;

    private String code;

    private String name;
    private String nameEn;

    private String shortDescription;
    private String shortDescriptionEn;

    private String description;
    private String descriptionEn;

    private String thumbnailUrl;
    private String mainImageUrl;

    private Boolean active = true;
    private Boolean inquiryEnabled = true;

    private Integer sortOrder;

    public static ProductForm from(Product product) {
        ProductForm form = new ProductForm();

        form.setCategoryId(product.getCategory().getId());
        form.setCode(product.getCode());
        form.setName(product.getName());
        form.setNameEn(product.getNameEn());
        form.setShortDescription(product.getShortDescription());
        form.setShortDescriptionEn(product.getShortDescriptionEn());
        form.setDescription(product.getDescription());
        form.setDescriptionEn(product.getDescriptionEn());
        form.setThumbnailUrl(product.getThumbnailUrl());
        form.setMainImageUrl(product.getMainImageUrl());
        form.setActive(product.getActive());
        form.setInquiryEnabled(product.getInquiryEnabled());
        form.setSortOrder(product.getSortOrder());

        return form;
    }
}