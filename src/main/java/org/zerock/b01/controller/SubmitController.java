package org.zerock.b01.controller;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Log4j2

public class SubmitController {

    private static final Logger log = LoggerFactory.getLogger(SubmitController.class);

    @PostMapping("/savevalue")
    public void saveValue(@RequestBody int Value){

        log.info("asdasdasdasdasdasdasdasdasd");

    }
}
