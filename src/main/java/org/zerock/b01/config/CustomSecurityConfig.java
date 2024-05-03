package org.zerock.b01.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
@Log4j2
@RequiredArgsConstructor

//책 691 이지만 글로벌은 안쓴다.<-인가 처리

@EnableMethodSecurity(prePostEnabled = true)
//시큐리티 설정창
public class CustomSecurityConfig {


    //p.703
    //Remember-me 서비르를 위해서 datasource와 customeruserdetailservice를 주입
    private final DataSource dataSource;

    private final UserDetailsService userDetailsService;

    //p.703 end

    //p.680
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
        log.info("----------------------------configuer----------------------------");
        //p.684


        http.formLogin(form -> {
            form.loginPage("/member/login");
        });
        //p.697
        //CSRF 설정.......................
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        //p.697 end
        //p.704
        //remember-me 설정
        http.rememberMe(httpSecurityRememberMeConfigurer -> {
            //기억할 키 값 설정하기
           httpSecurityRememberMeConfigurer.key("12345678")
                   .tokenRepository(persistentTokenRepository())
                   .userDetailsService(userDetailsService) //PasswordEncoder 에 의한 순환구조가 발생 할 수 있음
                   .tokenValiditySeconds(60*60*24*30);
        });
        //p.704 end



        return http.build();
    }//p.684 end

    //p.680 end

    //p.683
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        log.info("----------------------------web configure----------------------------");
        //정적 리소스 필터링 제외
        return (web -> web.ignoring().requestMatchers(
                PathRequest.toStaticResources().atCommonLocations()));
    }
    //p.683 end

    //p.688
    //password를 암호화 처리를 해준다
    @Bean
    public PasswordEncoder  passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //p.688 end


    //p704 bean등록
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
        //datasource주입
        repo.setDataSource(dataSource);
        return repo;
    }
    //p704 bean등록 끝


}

