package com.kqsf.domain.product.repository;

import com.kqsf.domain.product.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByActiveTrueOrderBySortOrderAsc();

    Optional<ProductCategory> findByCodeAndActiveTrue(String code);

}
