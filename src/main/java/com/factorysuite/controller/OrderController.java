package com.factorysuite.controller;

import com.factorysuite.dto.OrderItemDto;
import com.factorysuite.dto.PageDto;
import com.factorysuite.dto.OrderDto;
import com.factorysuite.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    //주문 등록
    @PostMapping("/post")
    public boolean orderInsert(@RequestBody OrderDto orderDto) {


        System.out.println("컨트롤러실행orderDto"+orderDto);
        boolean result = orderService.orderInsert( orderDto );
        System.out.println("컨트롤러반환orderDto"+orderDto);

        return result;
    }


    // 모든 주문 조회
    @GetMapping("/get")
    public PageDto getAll(@RequestParam int page, @RequestParam String key,
                          @RequestParam String keyword , String status,
                          @RequestParam String startDate, @RequestParam String endDate , @RequestParam int view){


        System.out.println("실행한다 주문 조회 컨트롤러.... "+key+keyword);

        PageDto result = orderService.getAll( page , key, keyword , status, startDate, endDate ,view);
        System.out.println("컨트롤러 주문 조회 결과값 >>>>>>"+result);
        return result;
        //System.out.println("컨트롤러회원조회 : "+result);


    }

    // 주문 1개 상세 조회
    @GetMapping("/getinfo")
    public OrderDto getInfo(@RequestParam int orderId){

        OrderDto result = orderService.getInfo(orderId);
        return result;
    }
/*

    // 주문 수정
    @PutMapping("/put")
    public boolean customerUpdate(@RequestBody OrderDto orderDto){
        boolean result = orderService.orderUpdate(orderDto);
        //System.out.println("컨트롤러 : "+starBugMemberDto);
        return result;
    }




    // 주문 삭제
    @DeleteMapping("/delete")
    public boolean customerDelete(@RequestParam int orderId){
        System.out.println("컨트롤러 : "+orderId);
        boolean result = orderService.orderDelete(orderId);
        return result;
    }
*/
}
