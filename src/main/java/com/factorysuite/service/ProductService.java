package com.factorysuite.service;

import com.factorysuite.dto.CustomerDto;
import com.factorysuite.dto.PageDto;
import com.factorysuite.dto.ProductDto;
import com.factorysuite.entity.CustomerEntity;
import com.factorysuite.entity.ProductEntity;
import com.factorysuite.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    //  등록
    @Transactional
    public boolean productInsert(ProductDto productDto){
        ProductEntity entity = productDto.productToEntity();
        // 등록시 삭제여부 기본값 "N"설정
        entity.setDeleteState("N");

        ProductEntity productEntity = productRepository.save(entity);
        if (productEntity.getProductId() >= 1){return true;}
        return false;
    }

    // 조회
    @Transactional
    public PageDto getAll( int page, String keyword ,  int view,  String category,  String forSale){
        System.out.println("실행한다 서비스.... ");

        keyword = (keyword == null) ? "" : keyword;
        category = (category == null) ? "" : category;
        forSale = (forSale == null) ? "" : forSale;

        // 페이징처리위한 인터페이스 사용
        Pageable pageable = PageRequest.of(page-1 , view , Sort.Direction.DESC ,"product_id");
        // 1. 모든게시물 호출
        // Sort로 "product_id"필드 기준으로 검색후 내림차순 - 페이징처리하기 전 코드
        Page<ProductEntity> productEntities = productRepository.findByproductserch(keyword, category, forSale, pageable);
        // entity -> dto 변환
        // list 객체에 선언후 담기
        List<ProductDto> productDtos = new ArrayList<>();
        productEntities.forEach( e ->{
            productDtos.add(e.productToDto()); // dto로 변환
        });

        // 총페이지수
        int totalPages = productEntities.getTotalPages();
        // 총게시물수
        Long totalCount = productEntities.getTotalElements();
        // Dto로 변환
        PageDto pageDto = PageDto.builder()
                .productDtos(productDtos)
                .totalPage(totalPages)
                .totalCount(totalCount)
                .build();
        // System.out.println("서비스 :"+pageDto);


        return pageDto;
    }


    // 제품 리스트 조회
    @Transactional
    public List<ProductDto> getList( ){
        System.out.println("실행한다 서비스.... ");
        // 1. 전체 조회
        List<ProductEntity> productEntities = productRepository.findByproductList();
        // 2. DTO 리스트 생성
        List<ProductDto> productDtos = new ArrayList<>();

        // 3. entity → dto 변환
        for (ProductEntity e : productEntities) {
            productDtos.add(e.productToDto());
        }
        return productDtos;
    }


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
}
