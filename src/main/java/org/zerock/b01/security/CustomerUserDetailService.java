package org.zerock.b01.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerUserDetailService implements UserDetailsService {
        //p.689
    //인코더를 불러오고 생성자 주입으로 final 선언
    //
        private   PasswordEncoder passwordEncoder; //주입하면 순환구조가 발생함
    //p.689 end

    //p.685
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByName : "+username );
        //p.687
        //userDetails 객체로 반환하는 userDetails를 생성
        //user1 이라는 사용자의 정보를 생성 한거다
        UserDetails userDetails= User.builder().username("user1")
                //------------p.689-------------//
                .password(passwordEncoder.encode("1111"))
                .authorities("ROLE_USER")//일반사용자
                .build();
                //------------p.689 end-------------//
        return userDetails;
        //p.687 end
    }
    //p.685 end
}
