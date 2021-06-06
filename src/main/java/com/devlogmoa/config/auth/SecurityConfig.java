package com.devlogmoa.config.auth;
import com.devlogmoa.domain.member.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity // 스프링 시큐리티 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable() //  h2-console 화면을 사용하기 위해 해당 옵션들 disable
                .and()
                .authorizeRequests() // 설정된 값 이외 나머지 URL
                .antMatchers("/", "/css/**", "/js/**", "/h2-console/**").permitAll()
                .antMatchers("/api/admin/**").hasRole(Role.ADMIN.name()) // 권한 관리 대상 지정 옵션
                // URL, HTTP 메소드별로9 관리 가능
                // "/" 등 지정된 URL들은 permitALL() 옵션으로 전체 열람 권한을 줌
                // 해당 주소는 USER 권한을 가진 사람만 접속 가능
                .anyRequest().authenticated() // anyRequest(). : 위의 설정 값 이외 URL은
//                 authenticated() : 인증된 사용자만 가능(로그인한 사용자)
                // 현재는 Role.USER만 사용 하므로 의미는 없음
                // Role권한을 주지 않고, 로그엔 체크만 하려면 이것만 사용하면 됨
                .and()
                .logout()
                .logoutSuccessUrl("/") // 로그아웃 성공 시 URL
                .and()
                .oauth2Login() // OAuth2 로그인 진입점
                .userInfoEndpoint() // OAuth2 로그인 성공 후 사용자 정보 가져올 때 설정들 담당
                .userService(customOAuth2UserService); // 소셜 로그인 성공 후 진행할 UserService 인터페이스의 구현체를 등록
        // 리소스 서버(즉, 소셜 서비스들)에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능을 명시할 수 있음
    }
}