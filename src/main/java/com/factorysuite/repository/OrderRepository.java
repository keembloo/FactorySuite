package com.factorysuite.repository;

import com.factorysuite.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Integer> {
    @Query
            (value ="select o.* from orders o " +
                    "join customer c on o.customer_id = c.customer_id " +
                    "where (:keyword = '' or " +
                    " (:key = 'customerName' and c.customer_name like %:keyword%) or " +
                    " (:key = 'orderNum' and cast(o.order_num as char) like %:keyword%) ) " +

                    "and (:status = '' or o.status = :status) " +

                    "and (:startDate is null or o.created_dt >= :startDate) " +
                    "and (:endDate is null or o.created_dt <= :endDate) " +

                    "and c.delete_state = 'N' ",

                    countQuery =
                            "select count(*) from orders o " +
                                    "join customer c on o.customer_id = c.customer_id " +

                                    "where (:keyword = '' or " +
                                    " (:key = 'customerName' and c.customer_name like %:keyword%) or " +
                                    " (:key = 'orderNum' and cast(o.order_num as char) like %:keyword%) ) " +

                                    "and (:status = '' or o.status = :status) " +

                                    "and (:startDate is null or o.created_dt >= :startDate) " +
                                    "and (:endDate is null or o.created_dt <= :endDate) " +

                                    "and c.delete_state = 'N' ",

                    nativeQuery = true)
    Page<OrderEntity> findByorderserch(String key, String keyword,
                                       String status, String startDate,
                                       String endDate, Pageable pageable);


    @Query
            (value ="select count(*) from orders o "+
                    "where o.created_dt BETWEEN :start AND :end ",
                    nativeQuery = true)
                   int countByToday(LocalDateTime start, LocalDateTime end);

}
