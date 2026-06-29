package com.kqsf.domain.product.service;

import com.kqsf.domain.product.dto.ProductCategoryResponse;
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
@Transactional(readOnly = true)
public class ProductQueryService {

    private final ProductRepository productRepository;
    private final ProductProcessingOptionRepository optionRepository;
    private final ProductCategoryRepository productCategoryRepository;

    public Page<ProductResponse> findVisibleProducts(Pageable pageable) {
        Page<Product> productPage = productRepository.findByActiveTrue(pageable);

        return toProductResponsePage(productPage);
    }

    public Page<ProductResponse> findVisibleProductsByCategoryCode(
            String categoryCode,
            Pageable pageable
    ) {
        Page<Product> productPage =
                productRepository.findByCategory_CodeAndCategory_ActiveTrueAndActiveTrue(
                        categoryCode,
                        pageable
                );

        return toProductResponsePage(productPage);
    }

    public ProductResponse findProductDetail(String categoryCode, String productCode) {
        Product product = productRepository.findDetail(categoryCode, productCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        List<ProductProcessingOption> options =
                optionRepository.findByProduct_IdAndActiveTrueOrderBySortOrderAsc(product.getId());

        return ProductResponse.from(product, options);
    }

    private Page<ProductResponse> toProductResponsePage(Page<Product> productPage) {
        List<Long> productIds = productPage.getContent()
                .stream()
                .map(Product::getId)
                .toList();

        if (productIds.isEmpty()) {
            return productPage.map(product -> ProductResponse.from(product, List.of()));
        }

        Map<Long, List<ProductProcessingOption>> optionsByProductId =
                optionRepository.findByProduct_IdInAndActiveTrueOrderBySortOrderAsc(productIds)
                        .stream()
                        .collect(Collectors.groupingBy(option -> option.getProduct().getId()));

        return productPage.map(product ->
                ProductResponse.from(
                        product,
                        optionsByProductId.getOrDefault(product.getId(), List.of())
                )
        );
    }


    public List<ProductCategoryResponse> findVisibleCategories() {
        return productCategoryRepository.findByActiveTrueOrderBySortOrderAsc()
                .stream()
                .map(ProductCategoryResponse::from)
                .toList();
    }

    public ProductCategoryResponse findCategoryByCode(String categoryCode) {
        ProductCategory category = productCategoryRepository.findByCodeAndActiveTrue(categoryCode)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 분류입니다."));

        return ProductCategoryResponse.from(category);

    }
}
