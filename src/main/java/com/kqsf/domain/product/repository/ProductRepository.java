package com.kqsf.domain.product.repository;

import com.kqsf.domain.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByActiveTrue(Pageable pageable);

    Page<Product> findByCategory_CodeAndCategory_ActiveTrueAndActiveTrue(
            String categoryCode,
            Pageable pageable
    );

    @Query("""
        select p
        from Product p
        join fetch p.category c
        where c.code = :categoryCode
          and p.code = :productCode
          and p.active = true
          and c.active = true
    """)
    Optional<Product> findDetail(
            @Param("categoryCode") String categoryCode,
            @Param("productCode") String productCode
    );

    @Query("""
        select distinct p
        from Product p
        join fetch p.category c
        left join fetch p.processingOptions po
        where p.id = :id
    """)
    Optional<Product> findByIdWithCategoryAndOptions(Long id);

    @Query("""
        select p
        from Product p
        join fetch p.category c
        order by p.id desc
    """)
    Page<Product> findAllWithCategory(Pageable pageable);

    @Query(
            value = """
                select p
                from Product p
                join fetch p.category c
                where c.id = :categoryId
                """,
            countQuery = """
                select count(p)
                from Product p
                where p.category.id = :categoryId
                """
    )
    Page<Product> findByCategoryIdWithCategory(Long categoryId, Pageable pageable);
}
