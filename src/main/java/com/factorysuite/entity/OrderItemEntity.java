package com.factorysuite.entity;

import com.factorysuite.dto.OrderItemDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_item")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = "orderEntity")
@Builder
public class OrderItemEntity extends BaseTime{

    // 주문아이템번호
    @Id
    @Column(name = "order_item_id" , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    // 주문수량
    @Column(name = "quantity", nullable = false)
    private int quantity;

    // 주문번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    // 제품번호
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;


    public OrderItemDto orderItemToDto() {
        return OrderItemDto.builder()
                .orderItemId(this.orderItemId)
                .quantity(this.quantity)
                .orderId(this.orderEntity.getOrderId())
                .productId(this.productEntity.getProductId())
                .build();
    }


}
