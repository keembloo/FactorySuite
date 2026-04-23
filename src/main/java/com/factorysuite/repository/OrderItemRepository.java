package com.factorysuite.repository;

import com.factorysuite.entity.OrderItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItemEntity, Integer> {
    @Query
            (value="select * from order_item where "+
                    " orderId = (:orderId) "
                    , nativeQuery = true)
    List<OrderItemEntity> findByorderItemserch(int orderId);


}
