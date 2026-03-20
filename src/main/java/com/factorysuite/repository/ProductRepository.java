package com.factorysuite.repository;

import com.factorysuite.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    Optional<ProductEntity> findById(int productid);
}
