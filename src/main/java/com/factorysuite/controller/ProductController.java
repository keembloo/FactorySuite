package com.factorysuite.controller;

import com.factorysuite.dto.CustomerDto;
import com.factorysuite.dto.ProductDto;
import com.factorysuite.service.ProductService;
import com.factorysuite.service.Userservice;
import com.factorysuite.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/productInsert")
    public boolean login(@RequestBody ProductDto productDto) {


        boolean result = productService.productInsert( productDto );
        System.out.println("컨트롤러productDto"+productDto);
        System.out.println("컨트롤러productDto"+productDto);

        return result;
    }
}
