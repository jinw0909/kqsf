//package com.kqsf.domain.product.entity;
//
//import com.kqsf.domain.product.enums.ProcessingType;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Getter
//@NoArgsConstructor
//public class ProductProcessingOption {
//
//    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    // 어떤 상품의 제공 형태인지
//    @Setter
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", nullable = false)
//    private Product product;
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private ProcessingType processingType;
//    // RAW, PROCESSED, OEM
//
//    @Column(nullable = false)
//    private String label;
//    // 원물, 가공, OEM
//
//    private String labelEn;
//    // Raw Material, Processed, OEM
//
//    @Column(length = 500)
//    private String description;
//    // 예: 원물 상태로 초저온 동결·보관된 제품
//
//    @Column(length = 500)
//    private String descriptionEn;
//
//    private Boolean active = true;
//
//    private Integer sortOrder;
//
//    public void update(
//            ProcessingType processingType,
//            String label,
//            String labelEn,
//            String description,
//            String descriptionEn,
//            Boolean active,
//            Integer sortOrder
//    ) {
//        this.processingType = processingType;
//        this.label = label;
//        this.labelEn = labelEn;
//        this.description = description;
//        this.descriptionEn = descriptionEn;
//        this.active = active;
//        this.sortOrder = sortOrder;
//    }
//
//}
package com.kqsf.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductProcessingOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_processing_option_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private String title;
    // 참치 로인, 원물 공급, OEM 가공, 고등어 필렛

    private String titleEn;

    @Column(length = 500)
    private String summary;

    @Column(length = 500)
    private String summaryEn;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String descriptionEn;

    private String imageUrl;

    private Boolean active = true;

    private Integer sortOrder;

    public static ProductProcessingOption create(
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
        ProductProcessingOption option = new ProductProcessingOption();
        option.title = title;
        option.titleEn = titleEn;
        option.summary = summary;
        option.summaryEn = summaryEn;
        option.description = description;
        option.descriptionEn = descriptionEn;
        option.imageUrl = imageUrl;
        option.active = active;
        option.sortOrder = sortOrder;
        return option;
    }

    public void update(
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
        this.title = title;
        this.titleEn = titleEn;
        this.summary = summary;
        this.summaryEn = summaryEn;
        this.description = description;
        this.descriptionEn = descriptionEn;
        this.imageUrl = imageUrl;
        this.active = active;
        this.sortOrder = sortOrder;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}