package com.factorysuite.controller;

import com.factorysuite.dto.CustomerDto;
import com.factorysuite.dto.PageDto;
import com.factorysuite.dto.ProductDto;
import com.factorysuite.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    //제품 등록
    @PostMapping("/post")
    public boolean login(@RequestBody ProductDto productDto) {


        boolean result = productService.productInsert( productDto );
        System.out.println("컨트롤러productDto"+productDto);
        System.out.println("컨트롤러productDto"+productDto);

        return result;
    }

    // 모든 제품 조회
    @GetMapping("/get")
    public PageDto getAll(@RequestParam int page,
                          @RequestParam String keyword , @RequestParam int view,
                          @RequestParam  String category,  @RequestParam String forSale){
        System.out.println("실행한다 조회 컨트롤러.... ");
        return productService.getAll( page , keyword , view, category , forSale);
        //System.out.println("컨트롤러회원조회 : "+result);


    }

    // 제품 리스트 조회
    @GetMapping("/getlist")
    public List<ProductDto> getList( ){
        System.out.println("실행한다 조회 컨트롤러.... ");
        return productService.getList();
        //System.out.println("컨트롤러회원조회 : "+result);


    }

    // 제품 수정
    @PutMapping("/put")
    public boolean customerUpdate(@RequestBody ProductDto productDto){
        boolean result = productService.productUpdate(productDto);
        //System.out.println("컨트롤러 : "+starBugMemberDto);
        return result;
    }



    // 제품 삭제
    @DeleteMapping("/delete")
    public boolean customerDelete(@RequestParam int productId){
        System.out.println("컨트롤러 : "+productId);
        boolean result = productService.productDelete(productId);
        return result;
    }

}
