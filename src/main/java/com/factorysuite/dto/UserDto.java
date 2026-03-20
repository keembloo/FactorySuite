package com.factorysuite.dto;

import com.factorysuite.entity.CustomerEntity;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import com.factorysuite.entity.UserEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {

    private int userid; // 사용자번호
    private String username; // 사용자아이디
    private String pwd; // 사용자 비밀번호
    private String name; // 사용자 이름
    private String role; // 사용자 권한
    private LocalDateTime createdDt; // 거래처 생성일

    public UserEntity userToEntity(){
        return UserEntity.builder()
                .userid(this.userid)
                .username(this.username)
                .pwd(this.pwd)
                .name(this.name)
                .role(this.role)
                .build();
    }


}
