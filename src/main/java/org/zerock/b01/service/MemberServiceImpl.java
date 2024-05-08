package org.zerock.b01.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.Member;
import org.zerock.b01.domain.MemberRole;
import org.zerock.b01.dto.MemberJoinDTO;
import org.zerock.b01.repository.MemberRespository;


@Log4j2
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRespository memberRespository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;


    @Override
    public void join(MemberJoinDTO memberJoinDTO) throws MidExistException {

        //존재 여부를 확인하기 위해서 받는다
        String mid = memberJoinDTO.getMid();

        //중복확인 있으면 트루 없으면 폴스
        boolean exist = memberRespository.existsById(mid);

        //만약 이그짓이 트루면...
        if(exist){ //중복시. 실행
            throw  new MidExistException( "아이디가 중복 되었습니다.");
        }

        //modelMapper로 memberJoinDTO에 있는 값을 Member 클래스로 변환... 반환
        Member member = modelMapper.map(memberJoinDTO , Member.class);
        member.changePassword(passwordEncoder.encode(memberJoinDTO.getMpw())); //패스워드 변경....인코딩처리
        member.addRole(MemberRole.USER); //권환 부여....

        //결과 확인을 위한 인포
        log.info("==================================================================\n"+
                "==================================================================\n");
        log.info(member);
        log.info(member.getRoleSet());
        log.info("==================================================================\n"+
                "==================================================================\n");
        //최종적으로 DB에 저장
        memberRespository.save(member);



    }
}
