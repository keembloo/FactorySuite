package com.factorysuite.dto;
import java.time.LocalDateTime;
import java.util.List;

import com.factorysuite.entity.OrderEntity;
import com.factorysuite.entity.ProductEntity;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class OrderDto {
    private int orderId; // 주문ID
    private String orderNum; //주문번호
    private String status; // 주문상태
    private String deleteState; // 주문 삭제여부
    private LocalDateTime orderDt; // 주문일시
    private int customerId; // 거래처번호
    private String customerName; //거래처이름

    private List<OrderItemDto> orderItemDtos;

    public OrderEntity orderToEntity(){
        return OrderEntity.builder()
                .orderId(this.orderId)
                .orderNum(this.orderNum)
                .status(this.status)
                .deleteState(this.deleteState)
                .build();
    }

}
