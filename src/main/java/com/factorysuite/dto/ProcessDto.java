package com.factorysuite.dto;

import com.factorysuite.entity.ProcessEntity;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProcessDto {
    private int processId; // 공정ID
    private String processName; // 공정명
    private String processCode; // 공정 코드
    private String inspectionYn; // 품질 검사 공정여부
    private String useYn; // 공정 사용여부
    private LocalDateTime createdDt; // 공정 생성일
    private LocalDateTime updatedDt; // 공정 수정일
    private String deleteState; //삭제여부

    public ProcessEntity processToEntity(){
        return ProcessEntity.builder()
                .processId(this.processId)
                .processName(this.processName)
                .processCode(this.processCode)
                .inspectionYn(this.inspectionYn)
                .useYn(this.useYn)
                .build();
    }
}
