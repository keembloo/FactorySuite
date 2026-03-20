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
    private int productid; // 제품 번호
    private String productname; // 제품 이름
    private int price; // 제품 가격
    private String category; // 제품 카테고리
    private LocalDateTime createdDt; //  제품 생성일

    public ProductEntity productToEntity(){
        return ProductEntity.builder()
                .productid(this.productid)
                .prductname(this.productname)
                .price(this.price)
                .category(this.category)
                .build();
    }
}
