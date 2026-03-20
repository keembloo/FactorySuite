package com.factorysuite.entity;

import com.factorysuite.dto.CustomerDto;
import jakarta.persistence.*;
import lombok.*;
import com.factorysuite.dto.UserDto;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserEntity extends BaseTime {

    // 거래처번호
    @Id
    @Column(name = "user_id" , nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userid;

    // 사용자 아이디
    @Column(name = "username", nullable = false)
    private String username;

    // 사용자 비밀번호
    @Column(name = "pwd", nullable = false)
    private String pwd;

    // 사용자이름
    @Column(name = "name", nullable = false)
    private String name;

    // 사용자권한
    @Column(name = "role", nullable = false)
    private String role;


    public UserDto userToDto() {
        // 사용자  등록, 수정, 삭제를 위한 dto
        return UserDto.builder()
                .userid(this.userid)
                .username(this.username)
                .pwd(this.pwd)
                .name(this.name)
                .role(this.role)
                .build();

    }
}

