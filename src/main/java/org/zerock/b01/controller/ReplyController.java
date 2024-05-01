package org.zerock.b01.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.dto.ReplyDTO;

import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
//response 라는 어노테이션이 들어가있기 때문에 데이터형태로 전달을한다.
public class ReplyController {

    //오퍼레이션은 실제 기능과 관계가 없음
    //홈페이지에서 확인을 가능케해준다.
    @Operation(summary = "Replies POST - 방식으로 댓글등록 ")

    //댓글 등록 미디어타입은 spring http로 선택해야한다 //제이슨 타입이 아니면 처리를 못한다
    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
    //응답객체를 만들어주는 ReponseEntity
    //request body 어노테이션은 전달해주는 데이터의 바디부분

    public Map<String,Long> register(
            //이 값을 증명하라는 뜻이다
            @Valid/*값검증*/ @RequestBody /*요청*/ ReplyDTO replyDTO,
            BindingResult bindingResult)throws BindException {
        log.info(replyDTO);
        //예외가 발생할수있도록 만들어주면된다
        if(bindingResult.hasErrors()){
            //이걸가지고 넘겨준다.
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = Map.of("rno",111L);
        //여기 까지는 동작이다.
        return resultMap;
        //값검증 1. bno 2. reply 3. 응답자
        //rest방식으로 서버를 만들면 예외처리를 해야한다.
    }

}
