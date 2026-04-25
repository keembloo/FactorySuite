package com.factorysuite.service;

import com.factorysuite.dto.PageDto;
import com.factorysuite.dto.ProcessDto;
import com.factorysuite.entity.ProcessEntity;
import com.factorysuite.repository.ProcessRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;


    //  등록
    @Transactional
    public boolean processInsert(ProcessDto processDto){
        ProcessEntity processEntity = processRepository.save(processDto.processToEntity());;
        if (processEntity.getProcessId() >= 1){return true;}
        return false;
    }
/*
    // 조회
    @Transactional
    public PageDto getAll(int page, String keyword , int view, String category, String forSale){
        System.out.println("실행한다 서비스.... ");

        keyword = (keyword == null) ? "" : keyword;
        category = (category == null) ? "" : category;
        forSale = (forSale == null) ? "" : forSale;

        // 페이징처리위한 인터페이스 사용
        Pageable pageable = PageRequest.of(page-1 , view , Sort.Direction.DESC ,"process_id");
        // 1. 모든게시물 호출
        // Sort로 "process_id"필드 기준으로 검색후 내림차순 - 페이징처리하기 전 코드
        Page<ProcessEntity> processEntities = processRepository.findByprocessserch(keyword, category, forSale, pageable);
        // entity -> dto 변환
        // list 객체에 선언후 담기
        List<ProcessDto> processDtos = new ArrayList<>();
        processEntities.forEach( e ->{
            processDtos.add(e.processToDto()); // dto로 변환
        });

        // 총페이지수
        int totalPages = processEntities.getTotalPages();
        // 총게시물수
        Long totalCount = processEntities.getTotalElements();
        // Dto로 변환
        PageDto pageDto = PageDto.builder()
                .processDtos(processDtos)
                .totalPage(totalPages)
                .totalCount(totalCount)
                .build();
        // System.out.println("서비스 :"+pageDto);


        return pageDto;
    }


    // 제품 리스트 조회
    @Transactional
    public List<ProcessDto> getList( ){
        System.out.println("실행한다 서비스.... ");
        // 1. 전체 조회
        List<ProcessEntity> processEntities = processRepository.findByprocessList();
        // 2. DTO 리스트 생성
        List<ProcessDto> processDtos = new ArrayList<>();

        // 3. entity → dto 변환
        for (ProcessEntity e : processEntities) {
            processDtos.add(e.processToDto());
        }
        return processDtos;
    }


    // 수정
    @Transactional
    public boolean processUpdate( ProcessDto processDto ){

        Optional<ProcessEntity> optionalEntity
                = processRepository.findById(processDto.getProcessId());    // dto에 담긴 번호를 조회
        if(optionalEntity.isPresent()){ // 있는 번호이면
            ProcessEntity processEntity = optionalEntity.get(); // 엔티티에 있는 데이터를 꺼냄
            processEntity.setProcessName(processDto.getProcessName());  // dto에 있는 데이터를 엔티티 각 필드에 맞게 저장
            processEntity.setPrice(processDto.getPrice());
            processEntity.setCategory(processDto.getCategory());
            return true;
        }
        return false;
    }

    //삭제
    @Transactional
    public boolean processDelete( int processId ){

        System.out.println("제품삭제 서비스>>>>>"+processId);
        Optional<ProcessEntity> optionalEntity = processRepository.findById(processId); // 회원번호 조회
        if (optionalEntity.isPresent()) { // 있는 회원번호이면
            ProcessEntity processEntity = optionalEntity.get(); // 엔티티에 있는 데이터를 꺼냄
            processEntity.setDeleteState("Y");  // 삭제 여부를 "Y"로 저장
            return true;
        }
        return false;
    }*/
}
