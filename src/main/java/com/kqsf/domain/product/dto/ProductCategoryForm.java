package com.kqsf.domain.product.dto;

import com.kqsf.domain.product.entity.ProductCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductCategoryForm {

    private String code;
    private String name;
    private String nameEn;
    private String description;
    private String descriptionEn;
    private String bannerUrl;
    private Integer sortOrder;
    private Boolean active = true;

    public static ProductCategoryForm from(ProductCategory category) {
        ProductCategoryForm form = new ProductCategoryForm();
        form.setCode(category.getCode());
        form.setName(category.getName());
        form.setNameEn(category.getNameEn());
        form.setDescription(category.getDescription());
        form.setDescriptionEn(category.getDescriptionEn());
        form.setBannerUrl(category.getBannerUrl());
        form.setSortOrder(category.getSortOrder());
        form.setActive(category.getActive());
        return form;
    }
}
