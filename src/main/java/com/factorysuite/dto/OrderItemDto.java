package com.factorysuite.dto;

import com.factorysuite.entity.OrderEntity;
import com.factorysuite.entity.OrderItemEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderItemDto {

    private int orderItemId; // 주문아이템번호
    private int quantity; // 주문수량
    private int productId; // 제품번호
    private int orderId; // 주문번호

    public OrderItemEntity orderItemToEntity(){
        return OrderItemEntity.builder()
                .orderItemId(this.orderItemId)
                .quantity(this.quantity)
                .build();
    }
}
