package org.zerock.b01.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Member;
import org.zerock.b01.dto.MemberSecurityDTO;
import org.zerock.b01.repository.MemberRespository;

import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  //p/727  이제 안쓴다   private final PasswordEncoder passwordEncoder;

    private final MemberRespository memberRespository;





    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername : "+username);

        //DB에 등록된 사용자 정보를 불러옴.
        Optional<Member> result = memberRespository.getWithRoles(username);

        //만약 사용자가 없다면 더 진행 되서는 안된다
        if(result.isEmpty()){
            throw new UsernameNotFoundException("username not Found.....");
        }

        Member member = result.get();

        //UserDetails 객체로 반환하는 userDetails를 생성...
        MemberSecurityDTO memberSecurityDTO = new MemberSecurityDTO(
                member.getMid(),
                member.getMpw(),
                member.getEmail(),
                member.isDel(),
                false,//소셜로 로그인 처리하지 않는 상황
                member.getRoleSet().stream().map(memberRole ->
                        new SimpleGrantedAuthority("ROLE_"+memberRole.name())).collect(Collectors.toList())


        );

        log.info("memberSecurityDTO");
        log.info(memberSecurityDTO);

         return memberSecurityDTO;
    }
}