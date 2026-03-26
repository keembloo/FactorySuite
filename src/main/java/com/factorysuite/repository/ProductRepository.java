package com.factorysuite.repository;

import com.factorysuite.entity.CustomerEntity;
import com.factorysuite.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    @Query
            (value="select * from product where "+
                    " if( :keyword = '' , true , "+
                    " if( :key = 'productName' , product_name like %:keyword% , "+
                    " if( :key = 'forSale' , for_sale like %:keyword% , " +
                    " if( :key = 'category' , category like %:keyword% , true ))))"
                    , nativeQuery = true)
    Page<ProductEntity> findByproductserch(String key, String keyword, Pageable pageable);
}
