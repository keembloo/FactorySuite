package com.factorysuite.repository;

import com.factorysuite.entity.OrderItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
    @Query
            (value="select * from order_item where "+
                    " if( :keyword = '' , true , "+
                    " if( :key = 'customerName' , customer_name like %:keyword% , "+
                    " if( :key = 'phone' , phone like %:keyword% , true )))" +
                    " and delete_state = 'N' "
                    , nativeQuery = true)
    Page<OrderItemEntity> findByorderItemserch(String key, String keyword, Pageable pageable);


}
