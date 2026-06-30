package com.kqsf.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ProductCategory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_category_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private String name;

    private String nameEn;

    private String description;

    private String descriptionEn;

    private String bannerUrl;

    private Integer sortOrder;

    private Boolean active = true;


    public void update(
            String code,
            String name,
            String nameEn,
            String description,
            String descriptionEn,
            String thumbnailUrl,
            Integer sortOrder,
            Boolean active
    ) {
        this.code = code;
        this.name = name;
        this.nameEn = nameEn;
        this.description = description;
        this.descriptionEn = descriptionEn;
        this.bannerUrl = thumbnailUrl;
        this.sortOrder = sortOrder;
        this.active = active;
    }


}
