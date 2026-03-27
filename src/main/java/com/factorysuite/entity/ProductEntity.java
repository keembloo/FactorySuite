package com.factorysuite.entity;

import com.factorysuite.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductEntity extends BaseTime {

    //  제품번호
    @Id
    @Column(name = "product_id" , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    // 제품이름
    @Column(name = "product_name", nullable = false)
    private String productName;

    // 제품가격
    @Column(name = "price", nullable = false)
    private int price;

    // 제품카테고리
    @Column(name = "category", nullable = false)
    private String category;

    // 제품판매여부
    @Column(name = "for_sale", nullable = false)
    private String forSale;

    // 제품삭제여부
    @Column(name = "delete_state", nullable = false)
    private String deleteState;


    public ProductDto productToDto() {
        // 거래처 등록, 수정, 삭제를 위한 dto
        return ProductDto.builder()
                .productId(this.productId)
                .productName(this.productName)
                .price(this.price)
                .category(this.category)
                .forSale(this.forSale)
                .deleteState(this.deleteState)
                .createdDt(this.getCreatedDt())
                .updatedDt(this.getUpdatedDt())
                .build();

    }
}
