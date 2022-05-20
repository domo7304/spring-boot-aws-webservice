package com.eddie.springboot.config.auth.dto;

import com.eddie.springboot.domain.user.User;
import lombok.Getter;

@Getter
public class SessionUser {
    // SessionUser 에는 인증된 사용자 정보만을 필요로 하므로 name, email, picture 만 필드로 선언
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user){
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}