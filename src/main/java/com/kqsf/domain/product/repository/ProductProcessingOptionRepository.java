package com.kqsf.domain.product.repository;

import com.kqsf.domain.product.entity.ProductProcessingOption;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ProductProcessingOptionRepository extends JpaRepository<ProductProcessingOption, Long> {

//    List<ProductProcessingOption> findByProduct_IdInAndActiveTrueOrderBySortOrderAsc(
//            List<Long> productIds
//    );
//
//    List<ProductProcessingOption> findByProduct_IdAndActiveTrueOrderBySortOrderAsc(
//            Long productId
//    );
//
//    List<ProductProcessingOption> findByProduct_IdInOrderBySortOrderAsc(List<Long> productIds);

    // 관리자 목록/상세용: 비활성 포함
    List<ProductProcessingOption> findByProduct_IdInOrderBySortOrderAsc(List<Long> productIds);

    List<ProductProcessingOption> findByProduct_IdOrderBySortOrderAsc(Long productId);

    Optional<ProductProcessingOption> findByIdAndProduct_Id(Long id, Long productId);

    // 공개 사이트용: active만
    List<ProductProcessingOption> findByProduct_IdInAndActiveTrueOrderBySortOrderAsc(List<Long> productIds);

    List<ProductProcessingOption> findByProduct_IdAndActiveTrueOrderBySortOrderAsc(Long productId);
}
