package com.factorysuite.controller;

import com.factorysuite.dto.PageDto;
import com.factorysuite.dto.ProcessDto;
import com.factorysuite.service.ProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/process")
@RequiredArgsConstructor
public class ProcessController{

    private final ProcessService processService;

    //공정 등록
    @PostMapping("/post")
    public boolean login(@RequestBody ProcessDto processDto) {


        boolean result = processService.processInsert( processDto );
        System.out.println("컨트롤러 공정 등록 processDto"+processDto);

        return result;
    }


    // 모든 공정 조회
    @GetMapping("/get")
    public PageDto getAll(@RequestParam int page, @RequestParam String key,
                          @RequestParam String keyword , @RequestParam int view,
                          @RequestParam String useYn ){
        System.out.println("실행한다 공정 조회 컨트롤러.... ");
        System.out.println("검색내용 : >>>>"+key+keyword);
        return processService.getAll( page , key,  keyword , view , useYn);


    }
/*
    // 제품 리스트 조회
    @GetMapping("/getlist")
    public List<ProductDto> getList( ){
        System.out.println("실행한다 조회 컨트롤러.... ");
        return productService.getList();
        //System.out.println("컨트롤러회원조회 : "+result);


    }
    */

    // 공정 수정
    @PutMapping("/put")
    public boolean processUpdate(@RequestBody ProcessDto processDto){
        System.out.println("공정 수정 컨트롤러 >>>"+processDto);
        boolean result = processService.processUpdate(processDto);
        return result;
    }


    // 공정 삭제
    @DeleteMapping("/delete")
    public boolean processDelete(@RequestParam int processId){
        System.out.println("컨트롤러 공정 삭제 : "+processId);
        boolean result = processService.processDelete(processId);
        return result;
    }



}
