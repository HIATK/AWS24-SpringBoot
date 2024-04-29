package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
//데이터를 그대로 전달하는 콘트롤러 = 레스트 컨트롤러
//순수한 데이터를 순수하게 전달한다
@RestController
@Log4j2
public class SampleJSONController {

    @GetMapping("/helloArr")
    public String[] helloArr(){
        log.info("helloArr---");
        //데이터를 만들어서 전달
        //JSON에서 ()는 객체를 말하고
        //{}데이터값
        return new String[] {"AAA","BBB","CCC"};
    }

}
