package com.factorysuite.repository;

import com.factorysuite.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    @Query(value =
            "select * from product where " +
                    "(:keyword = '' or product_name like %:keyword%) " +
                    "and (:category = '' or category = :category) " +
                    "and (:forSale = '' or for_sale = :forSale)" +
                    "and delete_state != 'Y'" ,
            countQuery =
                    "select count(*) from product where " +
                            "(:keyword = '' or product_name like %:keyword%) " +
                            "and (:category = '' or category = :category) " +
                            "and (:forSale = '' or for_sale = :forSale)" +
                            "and delete_state != 'Y'" ,
            nativeQuery = true)
    Page<ProductEntity> findByproductserch(String keyword, String category, String forSale, Pageable pageable);
}
