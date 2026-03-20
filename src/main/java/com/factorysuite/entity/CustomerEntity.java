package com.factorysuite.entity;
import jakarta.persistence.*;
import lombok.*;
import com.factorysuite.dto.CustomerDto;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@AllArgsConstructor @NoArgsConstructor @Getter @Setter @ToString @Builder
public class CustomerEntity extends BaseTime {

    // 거래처번호
    @Id
    @Column(name = "customer_id" , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int customerId;

    // 거래처이름
    @Column(name = "customer_name", nullable = false)
    private String customerName;

    // 거래처전화번호
    @Column(name = "phone", nullable = false)
    private String phone;

    // 거래주소
    @Column(name = "address", nullable = false)
    private String address;


    public CustomerDto customerToDto() {
        // 거래처 등록, 수정, 삭제를 위한 dto
            return CustomerDto.builder()
                    .customerId(this.customerId)
                    .customerName(this.customerName)
                    .phone(this.phone)
                    .address(this.address)
                    .build();

    }
}
