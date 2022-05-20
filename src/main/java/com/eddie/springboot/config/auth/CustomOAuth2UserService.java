package com.eddie.springboot.config.auth;

import com.eddie.springboot.config.auth.dto.OAuthAttributes;
import com.eddie.springboot.config.auth.dto.SessionUser;
import com.eddie.springboot.domain.user.User;
import com.eddie.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 현재 로그인 진행 중인 서비스를 구분하는 코드
        // 구글 로그인인지, 네이버 로그인인지 등 구분을 위해 사용
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드값
        // 구글의 경우 기본적으로 코드를 지원하지만, 네이버 카카오 등은 기본 지원 x. 구글의 기본 코드는 "sub"
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                                                .getUserInfoEndpoint().getUserNameAttributeName();

        // OAuth2UserService 를 통해 가져온 OAuth2User 의 attribute 를 담을 클래스
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        User user = saveOrUpdate(attributes);

        // 세션에 사용자 정보를 저장하기 위한 Dto 클래스. 왜 User 클래스를 쓰지 않고 새로 만들어서 쓰는가?
        // 해당 부분에서 User 클래스를 사용하면 '직렬화가 구현되어 있지 않다' 는 에러를 만난다.
        // 하지만 Entity 인 User 클래스에 직렬화 코드를 넣는 것은 생각해 볼 것이 많다.
        // Entity class 는 언제 다른 Entity 와 관계까 형성될지 모르며,
        // 이 경우 자식 Entity 도 직렬화 대상에 포함되니 성능 이슈, 부수 효과 등이 발생할 확률이 높다.
        // 때문에 직렬화 기능을 가진 세션 Dto 를 하나 추가로 만드는 것이 이후 유지보수에 많은 도움이 된다.
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    // 구글 사용자 정보가 업데이트 되었을 경우 이름, 프로필사진 등의 변경이 User Entity 에도 반영
    private User saveOrUpdate(OAuthAttributes attributes){
        User user = userRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());
        return userRepository.save(user);
    }
}
