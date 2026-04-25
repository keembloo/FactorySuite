package com.factorysuite.entity;

import com.factorysuite.dto.ProcessDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "process")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProcessEntity extends BaseTime{
    // 공정ID
    @Id
    @Column(name = "process_id" , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int processId;

    // 공정명
    @Column(name = "process_name", nullable = false)
    private String processName;

    // 공정코드
    @Column(name = "process_code", nullable = false)
    private String processCode;

    // 검사여부
    @Column(name = "inspection_yn", nullable = false)
    private String inspectionYn;

    // 사용여부
    @Column(name = "use_yn", nullable = false)
    private String useYn;


    public ProcessDto processToDto() {
        // 공정 등록, 수정, 삭제를 위한 dto 변환
        return ProcessDto.builder()
                .processId(this.processId)
                .processName(this.processName)
                .processCode(this.processCode)
                .inspectionYn(this.inspectionYn)
                .useYn(this.useYn)
                .createdDt(this.getCreatedDt())
                .updatedDt(this.getUpdatedDt())
                .build();

    }


}
