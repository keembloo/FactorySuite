package com.factorysuite.dto;

import lombok.*;
import com.factorysuite.dto.CustomerDto;

import java.util.List;

@Builder@Getter
@Setter@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
// List<UserDto> userDtos; // 사용자 페이징처리


    //-------------규리---------------------//
    List<CustomerDto> customerDtos; // 거래처 페이징처리
    List<ProductDto> productDtos; //  제품  페이징처리

    //2. 반환된 총 페이지수
    private int totalPage;
    //3. 반환된 총 게시물수
    private long totalCount;


}
