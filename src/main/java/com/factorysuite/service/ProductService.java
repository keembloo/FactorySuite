package com.factorysuite.service;

import com.factorysuite.dto.CustomerDto;
import com.factorysuite.dto.ProductDto;
import com.factorysuite.entity.CustomerEntity;
import com.factorysuite.entity.ProductEntity;
import com.factorysuite.repository.CustomerRepository;
import com.factorysuite.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;


    //  등록
    @Transactional
    public boolean productInsert(ProductDto productDto){

        ProductEntity productEntity = productRepository.save(productDto.productToEntity());
        if (productEntity.getProductid() >= 1){return true;}
        return false;
    }
    // 수정
    @Transactional
    public boolean productUpdate( ProductDto productDto ){

        Optional<ProductEntity> optionalEntity
                = productRepository.findById(productDto.getProductid());    // dto에 담긴 회원번호를 조회
        if(optionalEntity.isPresent()){ // 있는 회원번호이면
            ProductEntity productEntity = optionalEntity.get(); // 엔티티에 있는 데이터를 꺼냄
            productEntity.setPrductname(productDto.getProductname());  // dto에 있는 데이터를 엔티티 각 필드에 맞게 저장
            productEntity.setPrice(productDto.getPrice());
            productEntity.setCategory(productDto.getCategory());
            return true;
        }


        return false;
    }

}
