package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.zerock.b01.domain.Member;
import org.zerock.b01.domain.MemberRole;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;



    //CustomUserDetailsService를 수정해야한다.
    @Test
    public void isnertMemebers(){

        IntStream.rangeClosed(1,100).forEach( i ->{

            Member member = Member.builder()
                    .mid("member"+i)
                    .mpw(passwordEncoder.encode("1111"))
                    .email("email"+i+"@aaa.bbb")
                    .build();

            //권환설정을 해준다 p.724
            member.addRole(MemberRole.USER);


            //11개의 계정은 RoleAdmin 이라는 역할이 추가된다.
            if (i >= 90){
                member.addRole(MemberRole.ADMIN);
            }
            memberRepository.save(member);
        });

    }

    @Test
    public void testRead(){
        Optional<Member> result = memberRepository.getWithRoles("member100");
        Member member = result.orElseThrow();

        log.info(member);
        log.info(member.getRoleSet());
        member.getRoleSet().forEach(memberRole -> log.info(memberRole.name()));
    }
}
