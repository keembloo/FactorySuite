package com.factorysuite.entity;

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
    private int productid;

    // 제품이름
    @Column(name = "product_name", nullable = false)
    private String prductname;

    // 제품가격
    @Column(name = "price", nullable = false)
    private int price;

    // 제품카테고리
    @Column(name = "category", nullable = false)
    private String category;


    //거래처리스트
/*    @Builder.Default
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<CustomerEntity> customerEntities = new ArrayList<>();*/
}
