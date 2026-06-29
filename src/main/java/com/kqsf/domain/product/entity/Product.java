package com.kqsf.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id")
    private ProductCategory category;

    @Column(nullable = false, unique = true)
    private String code;
    // EAST_SEA_BLUEFIN_TUNA

    @Column(nullable = false)
    private String name;

    private String nameEn;

    @Column(length = 500)
    private String shortDescription;
    // 상품 카드나 목록에서 짧게 보여줄 설명

    @Column(length = 500)
    private String shortDescriptionEn;

    @Column(columnDefinition = "TEXT")
    private String description;
    // 상세 페이지 본문 설명

    @Column(columnDefinition = "TEXT")
    private String descriptionEn;

    private String thumbnailUrl;
    // 목록/카드용 이미지

    private String mainImageUrl;
    // 상세 상단 대표 이미지

    private Boolean active = true;
    // 사이트 노출 여부

    private Boolean inquiryEnabled = true;
    // 문의하기 선택지 노출 여부

    private Integer sortOrder;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductProcessingOption> processingOptions = new ArrayList<>();

    public void update(
            ProductCategory category,
            String code,
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
            Integer sortOrder
    ) {
        this.category = category;
        this.code = code;
        this.name = name;
        this.nameEn = nameEn;
        this.shortDescription = shortDescription;
        this.shortDescriptionEn = shortDescriptionEn;
        this.description = description;
        this.descriptionEn = descriptionEn;
        this.thumbnailUrl = thumbnailUrl;
        this.mainImageUrl = mainImageUrl;
        this.active = active;
        this.inquiryEnabled = inquiryEnabled;
        this.sortOrder = sortOrder;
    }

    public void addProcessingOption(ProductProcessingOption option) {
        this.processingOptions.add(option);
        option.setProduct(this);
    }

    public void clearProcessingOptions() {
        for (ProductProcessingOption option : this.processingOptions) {
            option.setProduct(null);
        }
        this.processingOptions.clear();
    }


}
