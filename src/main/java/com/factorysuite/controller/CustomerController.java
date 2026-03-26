package com.factorysuite.controller;

import com.factorysuite.dto.CustomerDto;
import com.factorysuite.dto.PageDto;
import com.factorysuite.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/post")
    public boolean customerInsert(@RequestBody CustomerDto customerDto) {

        String customername = customerDto.getCustomerName();
        String phone = customerDto.getPhone();
        String address = customerDto.getAddress();
        LocalDateTime createdate = customerDto.getCreatedDt();

        boolean result = customerService.customerInsert( customerDto );
        System.out.println("컨트롤러customername"+customername);
        System.out.println("컨트롤러phone"+phone);

        return result;
    }
    // 모든 회원 조회
    @GetMapping("/get")
    public PageDto getAll(@RequestParam int page , @RequestParam String key , @RequestParam String keyword , @RequestParam int view ){
        System.out.println("실행한다 조회 컨트롤러.... ");
        return customerService.getAll( page , key , keyword , view);
        //System.out.println("컨트롤러회원조회 : "+result);


    }


    // 거래처 수정
    @PutMapping("/put")
    public boolean customerUpdate(@RequestBody CustomerDto customerDto){
        System.out.println("수정 컨트롤러 아이디>>>>>>"+customerDto.getCustomerId());
        System.out.println("수정 컨트롤러 주소>>>>"+customerDto.getAddress());
        boolean result = customerService.customerUpdate(customerDto);
        //System.out.println("컨트롤러 : "+starBugMemberDto);
        return result;
    }



    // 거래처 삭제
    @DeleteMapping("/delete")
    public boolean customerDelete(@RequestParam int customerId){
        System.out.println("컨트롤러 : "+customerId);
        boolean result = customerService.customerDelete(customerId);
        return result;
    }


}
