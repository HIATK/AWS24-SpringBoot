package org.zerock.b01.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Log4j2

public class Custom403Handler implements AccessDeniedHandler {
    //p.716
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
            log.info("--------------------------- A C C E S S    |     D E N I E D ---------------------------");

            //404띄워 달라는 의미
            // custom403 에러가 발생하면 여기로
            response.setStatus(HttpStatus.FORBIDDEN.value());

            //JSON 요청이였는지 확인
        String contentType =  request.getHeader("Content-Type");

        //satrt with  시작 문자를 뭘로 하느냐 라는 얘기다
        //content 타입의 시작을 application 으로
        Boolean jsonRequetst = contentType.startsWith("application/json");
        log.info("isJSON : " + jsonRequetst);

        //일반 request인경우
        if(!jsonRequetst) {
            //잘 못된걸 띄워준다 에러라는 매개변수로 해당값을 넘기겠다는 의미다.
            response.sendRedirect("/member/login?error=ACCESS_DENIED");
        }

    }


}
