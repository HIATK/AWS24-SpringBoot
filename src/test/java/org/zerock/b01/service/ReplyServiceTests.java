package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Reply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;

import java.awt.print.Pageable;

@SpringBootTest
@Log4j2

public class ReplyServiceTests {

    @Autowired
    private ReplyService replyService;

    @Test
    public void testRegister(){
        ReplyDTO replyDTO = ReplyDTO.builder()
                .replyText("댓글입니다12")
                .replyer("댓글러")
                .bno(192L)
                .build();
       // replyService.register(replyDTO);
       log.info(replyService.register(replyDTO));

    }

    @Test
    public void readTest(){
        ReplyDTO replyDTO = replyService.read(208L);
        log.info(replyDTO);
    }


    @Test
    public void testGetListOfBoard(){
        Long bno = 191L;
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().build();
        PageResponseDTO<ReplyDTO> result = replyService.getListOfBOard(bno, pageRequestDTO);
        result.getDtoList().forEach(replyDTO -> log.info(replyDTO));


    }
}
