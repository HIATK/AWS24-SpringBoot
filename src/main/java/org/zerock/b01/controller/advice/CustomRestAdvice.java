package org.zerock.b01.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.util.HashMap;
import java.util.Map;
/*
Rest컨트롤러는 Representative state Transfer 의 약자로 분산 시스템을 위한 아키텍쳐다
네트워크를 경유해서 외부 서버에 접속하거나 필요한 정보를 불러오기 위한 구조라고 생각하면 된다
그리고 이 rest 개념을 바탕으로 설계된 시스템을 RESTFULL 이라고 표현한다
REST의 경우 클라이언트가 특정 URL에 접속하면 웹페이지를 그리는것이 아니라 특정 정보 또는 특정 처리 결과를 텍스트화 시킨다.
 */
@RestControllerAdvice //이 컨트롤러가 REST컨트롤러 어드바이스 임을 나타낸다.
@Log4j2
public class CustomRestAdvice {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    //예외를 처리하는 메서드
    public ResponseEntity<Map<String, String>> handlerBindException(BindException e) {
        //로깅
        log.error(e);
        //에러 메세지를 담을 MAP을 만들어준다
        Map<String, String> errorMap = new HashMap<>();
        //만약 에러가 있을경우 필드 에러를 순회하고 에러 메세지를 Map에 추가한다
        if(e.hasErrors()) {
            BindingResult bindingResult = e.getBindingResult();
                //에러 순회
            bindingResult.getFieldErrors().forEach(fieldError -> {
                errorMap.put(fieldError.getField(), fieldError.getCode());
            });
        }
        //에러 메세지를 담은 응답을 반환함
        return ResponseEntity.badRequest().body(errorMap);
    }

    //예외를 처리하는 메서드임을 나타내는 어노테이션이다
    @ExceptionHandler(DataIntegrityViolationException.class)
    //해당 메서드에서 반환하는 HTTP상태 코드를 EXCEPTION_FAILED로 설정하는 어노테이션
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    //map <- String 타입을 담아서 예외처리를하는 메서드
    public ResponseEntity<Map<String, String>> handlerFKException(Exception e) {
        log.error(e); //에러 로그
        //에러 메세지를 담을 맵을 생성
        Map<String, String> errorMap = new HashMap<>();
        //현재 시간을 에러메세지에 추가한다.
        errorMap.put("time", ""+System.currentTimeMillis());
        //에러메세지
        errorMap.put("msg","constraint fails");
        //리턴으로 에러맵->바디->배드리퀘스트 를 담은 응답을 반환해준다.
        return ResponseEntity.badRequest().body(errorMap);

    }


}