package com.eddie.springboot.config.auth;

import com.eddie.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAUth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/h2-console").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .anyRequest().authenticated()
                .and()
                .logout().logoutSuccessUrl("/")
                .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(customOAUth2UserService);
    }
}
/*
    .csrf().disable().headers().frameOptions().disable() : h2-console 화면을 사용하기 위해 해당 옵션 disable
    .authorizeRequests() : URL 별 권한 관리를 설정하는 옵션의 시작점
    .antMatchers("/", "/h2-console").permitAll() : 모두에게 허용
    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) : USER 권한 사용자에게만 허용
    .anyRequest().authenticated() : 나머지 URL 들은 모두 인증된 사용자들에게만 허용, 즉 로그인한 사용자들
    .logout().logoutSuccessUrl("/") : 로그아웃 기능에 대한 여러 설정 진입점. 로그아웃 성공 시 / 주소로 이동
    .oauth2Login().userInfoEndpoint() : OAuth 2 로그인 성공 이후 사용자 정보를 가져올 때의 설정들을 담당
    userService : 소셜 로그인 성공 시 후속 조치를 진행할 UserService 인터페이스의 구현체를 등록
                  소셜 서비스로부터 가져온 사용자 정보에 대해 추가적으로 진행하고 싶은 기능을 명시할 수 있음
*/