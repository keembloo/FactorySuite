package com.factorysuite.dto;

import lombok.*;
import com.factorysuite.entity.CustomerEntity;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @ToString @Builder
public class CustomerDto {

    private int customerId; // 거래처번호
    private String customerName; // 거래처이름
    private String phone; // 거래처 전화번호
    private String address; // 거래처 주소
    private LocalDateTime createdDt; // 거래처 생성일

    public CustomerEntity customerToEntity(){
        return CustomerEntity.builder()
                .customerId(this.customerId)
                .customerName(this.customerName)
                .phone(this.phone)
                .address(this.address)
                .build();
    }

}

