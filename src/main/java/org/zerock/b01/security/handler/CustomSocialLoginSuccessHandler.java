package org.zerock.b01.security.handler;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.b01.dto.MemberSecurityDTO;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final PasswordEncoder passwordEncoder;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {

        log.info("------------------------------------------------------------------------------------------------------" +
                "--------------------------------------------------------------------------------------------------------" +
                "----------------------------------------------------------------------------------------------------------" +
                "-------------------------------------------------------------------------------------------------------" +
                "-------------------------------------------------------------------------------------------------------" +
                "-------------------------------------------------------------------------------------------------------" +
                "--------------------------------------------------------------------------------------------------------" +
                "--------------------------------------------------------------------------------------------------------" +
                "");
        log.info("CustomLoginSuccessHandler on AutheticationSucess");
        log.info(authentication.getPrincipal());

        //형변환
        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO)authentication.getPrincipal();

        //인코딩 되어있음
        //소셜 로그인이고 회우너 패스워드 1111
        String encodePW = memberSecurityDTO.getMpw();

        if (memberSecurityDTO.isSocial() && (memberSecurityDTO.getMpw()
                //matches   는 불리타입이여서 트루 폴스로 반환함
                .equals("1111") || passwordEncoder.matches("1111",memberSecurityDTO.getMpw()))){
                log.info("should Change PassWord");
                log.info("Redirect to Member Modify");
                response.sendRedirect("/member/modify");
                return;
        }else {
            response.sendRedirect("/board/list");
        }




    }
}
