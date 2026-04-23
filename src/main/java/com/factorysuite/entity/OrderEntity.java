package com.factorysuite.entity;

import com.factorysuite.dto.OrderDto;
import com.factorysuite.dto.OrderItemDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "orderItemEntities")
@Builder
public class OrderEntity extends BaseTime{
    // 주문ID
    @Id
    @Column(name = "order_id" , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    // 주문 번호 (String)
    @Column(name = "order_num" , nullable = false)
    private String orderNum;

    // 주문상태
    @Column(name = "status", nullable = false)
    private String status;

    // 주문삭제여부
    @Column(name = "delete_state", nullable = false)
    private String deleteState;

    // 주문 총금액
    @Column(name = "total_price", nullable = false)
    private int totalPrice;


    // 거래처번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private CustomerEntity customerEntity;


    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "orderEntity", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderItemEntity> orderItemEntities = new ArrayList<>();


    public OrderDto orderToDto() {
        return OrderDto.builder()
                .orderId(this.orderId)
                .orderNum(this.orderNum)
                .status(this.status)
                .deleteState(this.deleteState)
                .orderDt(this.getCreatedDt())
                .totalPrice(this.totalPrice)
                .customerId(
                        this.customerEntity != null ? this.customerEntity.getCustomerId() : null
                )
                .customerName(
                        this.customerEntity != null ? this.customerEntity.getCustomerName() : null
                )
                .orderItemDtos(
                        this.orderItemEntities.stream()
                                .map(OrderItemEntity::orderItemToDto)
                                .toList()
                )
                .build();
    }

}
