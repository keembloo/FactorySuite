package com.factorysuite.dto;

import com.factorysuite.entity.CustomerEntity;
import com.factorysuite.entity.ProductEntity;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto {
    private int productId; // 제품 번호
    private String productName; // 제품 이름
    private int price; // 제품 가격
    private String category; // 제품 카테고리
    private String forSale; // 제품 판매여부
    private String deleteState; // 제품 삭제여부
    private LocalDateTime createdDt; // 제품 생성일
    private LocalDateTime updatedDt; // 제품 수정일


    public ProductEntity productToEntity(){
        return ProductEntity.builder()
                .productId(this.productId)
                .productName(this.productName)
                .price(this.price)
                .category(this.category)
                .forSale(this.forSale)
                .deleteState(this.deleteState)
                .build();
    }
}
