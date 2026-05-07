package com.factorysuite.controller;

import com.factorysuite.service.ProcessFlowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/processFlow")
@RequiredArgsConstructor
public class ProcessFlowController {

    private final ProcessFlowService processFlowService;

/*
    //공정 등록
    @PostMapping("/post")
    public boolean login(@RequestBody ProcessDto processDto) {


        boolean result = processService.processInsert( processDto );
        System.out.println("컨트롤러 공정 등록 processDto"+processDto);

        return result;
    }
*/


    // 모든 공정 조회
    @GetMapping("/get")
    public boolean getAll(@RequestParam int productId ){
        System.out.println("실행한다 공정순서 조회 컨트롤러.... ");
        System.out.println("검색내용 : >>>>"+productId);
        return processFlowService.getAll( productId );


    }
/*
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
    }*/


}
