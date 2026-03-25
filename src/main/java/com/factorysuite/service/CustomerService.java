package com.factorysuite.service;

import com.factorysuite.dto.PageDto;
import com.factorysuite.entity.CustomerEntity;
import com.factorysuite.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.factorysuite.dto.CustomerDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    //  등록
    @Transactional
    public boolean customerInsert(CustomerDto customerDto){
        System.out.println("customerDto = " + customerDto);
        CustomerEntity customerEntity = customerRepository.save(customerDto.customerToEntity());
        if (customerEntity.getCustomerId() >= 1){return true;}
        return false;
    }

    // 조회
    @Transactional
    public PageDto getAll(int page , String key , String keyword , int view){
        System.out.println("실행한다 서비스.... ");

        // 페이징처리위한 인터페이스 사용
        Pageable pageable = PageRequest.of(page-1 , view , Sort.Direction.DESC ,"customer_id");
        // 1. 모든게시물 호출
        // Sort로 "customer_id"필드 기준으로 검색후 내림차순 - 페이징처리하기 전 코드
        Page<CustomerEntity> customerEntities = customerRepository.findBycustomerserch(key , keyword , pageable );
        // entity -> dto 변환
        // list 객체에 선언후 담기
        List<CustomerDto> customerDtos = new ArrayList<>();
        customerEntities.forEach( e ->{
            customerDtos.add(e.customerToDto()); // dto로 변환
        });

        // 총페이지수
        int totalPages = customerEntities.getTotalPages();
        // 총게시물수
        Long totalCount = customerEntities.getTotalElements();
        // Dto로 변환
        PageDto pageDto = PageDto.builder()
                .customerDtos(customerDtos)
                .totalPage(totalPages)
                .totalCount(totalCount)
                .build();
        // System.out.println("서비스 :"+pageDto);


        return pageDto;
    }

    // 수정
    @Transactional
    public boolean customerUpdate( CustomerDto customerDto ){
        System.out.println("실행한다 서비스.... 수정 ");
        System.out.println("수정할 주소 >>>>"+customerDto.getAddress());

        Optional<CustomerEntity> optionalEntity
                = customerRepository.findById(customerDto.getCustomerId());    // dto에 담긴 id를 조회
        if(optionalEntity.isPresent()){ // 있는 번호이면
            CustomerEntity customerEntity = optionalEntity.get(); // 엔티티에 있는 데이터를 꺼냄
            customerEntity.setCustomerName(customerDto.getCustomerName());  // dto에 있는 데이터를 엔티티 각 필드에 맞게 저장
            customerEntity.setAddress(customerDto.getAddress());
            customerEntity.setPhone(customerDto.getPhone());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean customerDelete( int customerId ){

        System.out.println("거래처삭제 서비스>>>>>"+customerId);
        Optional<CustomerEntity> optionalEntity = customerRepository.findById(customerId); // 회원번호 조회
        if (optionalEntity.isPresent()) { // 있는 회원번호이면
            CustomerEntity customerEntity = optionalEntity.get(); // 엔티티에 있는 데이터를 꺼냄
            customerEntity.setCustomerName("삭제된 거래처");  // 회원 이름을 "탈퇴한 회원"으로 저장
            return true;
        }
        return false;
    }

}
