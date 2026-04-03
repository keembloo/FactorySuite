package com.factorysuite.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.factorysuite.entity.CustomerEntity;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;


@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer>{
    @Query
    (value="select * from customer where "+
            " if( :keyword = '' , true , "+
            " if( :key = 'customerName' , customer_name like %:keyword% , "+
            " if( :key = 'phone' , phone like %:keyword% , true )))" +
            " and delete_state = 'N' "
            , nativeQuery = true)
    Page<CustomerEntity> findBycustomerserch(String key, String keyword, Pageable pageable);

    @Query
            (value="select * from customer where "+
                    " delete_state = 'N' "
                    , nativeQuery = true)
    List<CustomerEntity> findBycustomerlist();

}


