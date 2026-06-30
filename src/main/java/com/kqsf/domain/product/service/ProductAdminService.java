package com.kqsf.domain.product.service;

import com.kqsf.domain.product.dto.ProductCategoryForm;
import com.kqsf.domain.product.dto.ProductForm;
import com.kqsf.domain.product.dto.ProductProcessingOptionForm;
import com.kqsf.domain.product.dto.ProductResponse;
import com.kqsf.domain.product.entity.Product;
import com.kqsf.domain.product.entity.ProductCategory;
import com.kqsf.domain.product.entity.ProductProcessingOption;
import com.kqsf.domain.product.repository.ProductCategoryRepository;
import com.kqsf.domain.product.repository.ProductProcessingOptionRepository;
import com.kqsf.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductAdminService {

    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ProductProcessingOptionRepository optionRepository;

    @Transactional(readOnly = true)
    public List<ProductCategory> findAllCategories() {
        return productCategoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ProductCategory findCategory(Long id) {
        return productCategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 분류입니다."));
    }

    public Long createCategory(ProductCategoryForm form) {
        ProductCategory category = new ProductCategory();

        category.update(
                form.getCode(),
                form.getName(),
                form.getNameEn(),
                form.getDescription(),
                form.getDescriptionEn(),
                form.getBannerUrl(),
                form.getSortOrder(),
                Boolean.TRUE.equals(form.getActive())
        );

        productCategoryRepository.save(category);

        return category.getId();
    }

    public void updateCategory(Long id, ProductCategoryForm form) {
        ProductCategory category = findCategory(id);

        category.update(
                form.getCode(),
                form.getName(),
                form.getNameEn(),
                form.getDescription(),
                form.getDescriptionEn(),
                form.getBannerUrl(),
                form.getSortOrder(),
                Boolean.TRUE.equals(form.getActive())
        );
    }

    @Transactional(readOnly = true)
    public Product findProduct(Long id) {
        return productRepository.findByIdWithCategoryAndOptions(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public Long createProduct(ProductForm form) {
        ProductCategory category = findCategory(form.getCategoryId());

        Product product = new Product();

        product.update(
                category,
                form.getCode(),
                form.getName(),
                form.getNameEn(),
                form.getShortDescription(),
                form.getShortDescriptionEn(),
                form.getDescription(),
                form.getDescriptionEn(),
                form.getThumbnailUrl(),
                form.getMainImageUrl(),
                Boolean.TRUE.equals(form.getActive()),
                Boolean.TRUE.equals(form.getInquiryEnabled()),
                form.getSortOrder()
        );

        productRepository.save(product);

        return product.getId();
    }

    public void updateProduct(Long id, ProductForm form) {
        Product product = findProduct(id);
        ProductCategory category = findCategory(form.getCategoryId());

        product.update(
                category,
                form.getCode(),
                form.getName(),
                form.getNameEn(),
                form.getShortDescription(),
                form.getShortDescriptionEn(),
                form.getDescription(),
                form.getDescriptionEn(),
                form.getThumbnailUrl(),
                form.getMainImageUrl(),
                Boolean.TRUE.equals(form.getActive()),
                Boolean.TRUE.equals(form.getInquiryEnabled()),
                form.getSortOrder()
        );
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findProductPage(Long categoryId, Pageable pageable) {
        Page<Product> productPage;

        if (categoryId == null) {
            productPage = productRepository.findAllWithCategory(pageable);
        } else {
            productPage = productRepository.findByCategoryIdWithCategory(categoryId, pageable);
        }

        return attachOptions(productPage);
    }

    private Page<ProductResponse> attachOptions(Page<Product> productPage) {
        List<Long> productIds = productPage.getContent()
                .stream()
                .map(Product::getId)
                .toList();

        if (productIds.isEmpty()) {
            return productPage.map(product -> ProductResponse.from(product, List.of()));
        }

        Map<Long, List<ProductProcessingOption>> optionsByProductId =
                optionRepository.findByProduct_IdInOrderBySortOrderAsc(productIds)
                        .stream()
                        .collect(Collectors.groupingBy(option -> option.getProduct().getId()));

        return productPage.map(product ->
                ProductResponse.from(
                        product,
                        optionsByProductId.getOrDefault(product.getId(), List.of())
                )
        );
    }

    @Transactional(readOnly = true)
    public ProductProcessingOption findOption(Long productId, Long optionId) {
        return optionRepository.findByIdAndProduct_Id(optionId, productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 옵션입니다."));
    }

    public Long createOption(Long productId, ProductProcessingOptionForm form) {
        Product product = findProduct(productId);

        ProductProcessingOption option = ProductProcessingOption.create(
                form.getTitle(),
                form.getTitleEn(),
                form.getSummary(),
                form.getSummaryEn(),
                form.getDescription(),
                form.getDescriptionEn(),
                form.getImageUrl(),
                Boolean.TRUE.equals(form.getActive()),
                form.getSortOrder()
        );

        product.addProcessingOption(option);

        return option.getId();
    }

    public void updateOption(
            Long productId,
            Long optionId,
            ProductProcessingOptionForm form
    ) {
        ProductProcessingOption option = findOption(productId, optionId);

        option.update(
                form.getTitle(),
                form.getTitleEn(),
                form.getSummary(),
                form.getSummaryEn(),
                form.getDescription(),
                form.getDescriptionEn(),
                form.getImageUrl(),
                Boolean.TRUE.equals(form.getActive()),
                form.getSortOrder()
        );
    }
}