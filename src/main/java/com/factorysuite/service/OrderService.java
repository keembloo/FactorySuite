package com.factorysuite.service;

import com.factorysuite.dto.OrderDto;
import com.factorysuite.dto.OrderItemDto;
import com.factorysuite.dto.PageDto;
import com.factorysuite.entity.CustomerEntity;
import com.factorysuite.entity.OrderEntity;
import com.factorysuite.entity.ProductEntity;
import com.factorysuite.entity.OrderItemEntity;
import com.factorysuite.repository.CustomerRepository;
import com.factorysuite.repository.OrderItemRepository;
import com.factorysuite.repository.OrderRepository;
import com.factorysuite.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    //  등록
    @Transactional
    public boolean orderInsert(OrderDto orderDto){
        OrderEntity entity = orderDto.orderToEntity();
        // 등록시 삭제여부 기본값 "N"설정
        entity.setDeleteState("N");
        entity.setStatus("대기");
        System.out.println("orderDto>>>>>>>"+orderDto);

        // 있는 거래처이면 주문내역 저장
        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(orderDto.getCustomerId());
        if (optionalCustomer.isPresent()){
            CustomerEntity customer = optionalCustomer.get();

            // 주문에 customer 저장
            entity.setCustomerEntity(customer);

            // 오늘 주문 개수 조회
            LocalDate today = LocalDate.now();
            LocalDateTime start = today.atStartOfDay();
            LocalDateTime end = today.atTime(23, 59, 59);
            int orderCount = orderRepository.countByToday(start , end);
            System.out.println("서비스 orderCount:>>>>>>> "+orderCount);
            // 주문에 주문번호 생성하여 저장
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            entity.setOrderNum("ORDMES" + date + String.format("%06d", orderCount + 1));


            // 주문 저장
            OrderEntity savedOrder = orderRepository.save(entity);
            // 주문 저장 성공시 주문 상세 내역 저장
            for (OrderItemDto dto : orderDto.getOrderItemDtos()) {
                OrderItemEntity item = new OrderItemEntity();
                item.setOrderEntity(savedOrder);

                // 상품 연결
                ProductEntity product = productRepository.findById(dto.getProductId())
                        .orElseThrow(() -> new RuntimeException("상품 없음"));

                item.setProductEntity(product);
                item.setQuantity(dto.getQuantity());
                orderItemRepository.save(item);
            }

            return true;
        }

        return false;
    }

    // 조회
    @Transactional
    public PageDto getAll(int page, String key, String keyword, String status,
                          String startDate, String endDate, int view){
        System.out.println("실행한다 주문 조회서비스.... ");

        keyword = (keyword == null) ? "" : keyword;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // endDate 없으면 오늘
        if (endDate == null || endDate.trim().isEmpty()) {
            endDate = LocalDateTime.now().format(formatter);
        }

        // startDate 없으면 오늘 - 1개월
        if (startDate == null || startDate.trim().isEmpty()) {
            startDate = LocalDateTime.now()
                    .minusMonths(1)
                    .format(formatter);
        }


        // 페이징처리위한 인터페이스 사용
        Pageable pageable = PageRequest.of(page-1 , view , Sort.Direction.DESC ,"order_id");
        // 1. 모든게시물 호출
        // Sort로 "product_id"필드 기준으로 검색후 내림차순 - 페이징처리하기 전 코드
        Page<OrderEntity> orderEntities = orderRepository.findByorderserch(key, keyword, status, startDate, endDate, pageable);
        // entity -> dto 변환
        // list 객체에 선언후 담기
        List<OrderDto> orderDtos = new ArrayList<>();
        orderEntities.forEach( e ->{
            orderDtos.add(e.orderToDto()); // dto로 변환
        });

        // 총페이지수
        int totalPages = orderEntities.getTotalPages();
        // 총게시물수
        Long totalCount = orderEntities.getTotalElements();
        // Dto로 변환
        PageDto pageDto = PageDto.builder()
                .orderDtos(orderDtos)
                .totalPage(totalPages)
                .totalCount(totalCount)
                .build();
         System.out.println("주문 조회 서비스>>>>>> :"+pageDto);


        return pageDto;
    }

    // 주문 1개 상세 조회
    @Transactional
    public OrderDto getInfo(int orderId){
        //System.out.println("서비스 mno : "+mno);
        // 주문번호pk를 이용해 엔티티 찾기
        Optional<OrderEntity> orderEntityOptional = orderRepository.findById(orderId);
        // orderEntityOptional 안에 엔티티가있으면
        if (orderEntityOptional.isPresent()){
            // Optional 엔티티꺼내서 orderEntity 넣기
            OrderEntity orderEntity = orderEntityOptional.get();

            // 주문 상세 내역 dto 변환 list이기때문에 map함수 사용
            List<OrderItemDto> orderItemDtos = orderEntity.getOrderItemEntities().stream()
                    .map(orderItemEntity ->
                            OrderItemDto.builder()
                                    .orderItemId(orderItemEntity.getOrderItemId())        //주문상세번호
                                    .quantity(orderItemEntity.getQuantity())          //주문수량
                                    .productId(orderItemEntity.getProductEntity().getProductId())        //제품번호
                                    .productName(orderItemEntity.getProductEntity().getProductName()) //제품명
                                    .price(orderItemEntity.getProductEntity().getPrice())   // 가격
                                    .orderId(orderItemEntity.getOrderEntity().getOrderId())      //주문번호
                                    .build()
                    )
                    .collect(Collectors.toList());

            // OrderDto를 빌더 패턴으로 생성후 반환
            return OrderDto.builder()
                    .orderId(orderEntity.getOrderId())
                    .orderNum(orderEntity.getOrderNum())
                    .status(orderEntity.getStatus())
                    .orderDt(orderEntity.getCreatedDt())
                    .customerId(orderEntity.getCustomerEntity().getCustomerId())
                    .orderItemDtos(orderItemDtos)   // orderItemDtos dto 필드에 선언후 담아줌
                    .build();
        }
        return null;
    }
/*
    // 수정
    @Transactional
    public boolean productUpdate( ProductDto productDto ){

        Optional<ProductEntity> optionalEntity
                = productRepository.findById(productDto.getProductId());    // dto에 담긴 번호를 조회
        if(optionalEntity.isPresent()){ // 있는 번호이면
            ProductEntity productEntity = optionalEntity.get(); // 엔티티에 있는 데이터를 꺼냄
            productEntity.setProductName(productDto.getProductName());  // dto에 있는 데이터를 엔티티 각 필드에 맞게 저장
            productEntity.setPrice(productDto.getPrice());
            productEntity.setCategory(productDto.getCategory());
            return true;
        }
        return false;
    }

    //삭제
    @Transactional
    public boolean productDelete( int productId ){

        System.out.println("제품삭제 서비스>>>>>"+productId);
        Optional<ProductEntity> optionalEntity = productRepository.findById(productId); // 회원번호 조회
        if (optionalEntity.isPresent()) { // 있는 회원번호이면
            ProductEntity productEntity = optionalEntity.get(); // 엔티티에 있는 데이터를 꺼냄
            productEntity.setDeleteState("Y");  // 삭제 여부를 "Y"로 저장
            return true;
        }
        return false;
    }

 */
}
