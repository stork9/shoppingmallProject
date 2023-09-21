package com.example.day0706.config;

import com.example.day0706.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@EnableWebSecurity // 1
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter { // 2

    private final MemberService memberService; // 3

    @Override
    public void configure(WebSecurity web) { // 4
        web.ignoring().antMatchers("/assets/**", "/images/**", "/videos/**" ,"/error","/farvicon.ico","/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // 5
        http
                .csrf().disable()
                .authorizeRequests() // 6
                .antMatchers("/api/**","/api").permitAll() // 누구나 접근 허용
                .antMatchers("/user/**","/user").access("hasRole('ADMIN') or hasRole('USER')") // USER, ADMIN만 접근 가능
                .antMatchers("/admin/**","/admin").hasRole("ADMIN") // ADMIN만 접근 가능
                .anyRequest().authenticated() // 나머지 요청들은 권한의 종류에 상관 없이 권한이 있어야 접근 가능
                .and()
                .formLogin() // 7
                .loginPage("/api/login") // 로그인 페이지 링크
                .usernameParameter("userId")// 아이디 파라미터명 설정
                .passwordParameter("password")// 패스워드 파라미터명 설정
                .loginProcessingUrl("/api/loginPrc")
                .defaultSuccessUrl("/api/itemList/0",true)
                .failureUrl("/api/login")
                .permitAll()
                .and()
                .logout() // 8
                .logoutSuccessUrl("/") // 로그아웃 성공시 리다이렉트 주소
                .deleteCookies("JSESSIONID") // 로그아웃 후 쿠키 삭제
                .invalidateHttpSession(true) // 세션 날리기
        ;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception { // 9
        auth.userDetailsService(memberService)
                // 해당 서비스(userService)에서는 UserDetailsService를 implements해서
                // loadUserByUsername() 구현해야함 (서비스 참고)
                .passwordEncoder(new BCryptPasswordEncoder());
    }
}