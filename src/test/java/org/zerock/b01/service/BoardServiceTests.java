package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

import java.nio.file.NoSuchFileException;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
@Log4j2

public class BoardServiceTests {
    @Autowired
    private BoardService boardService;

    //의존성 주입
    @Test
    public void testRegister(){
        log.info(boardService.getClass().getName()); //클래스 이름을 알아온다.
        //오브젝트 클래스의 메서드로 해당객체의 클래스객체를 반환하고
        //getClass는 boardService 객체의 클래스 정보를 가져오고,
        //getname은 그 클래스정보 중에서 클래스 이름을 가져오는 것이다.

        BoardDTO boardDTO = BoardDTO.builder()
                .title("Sample1 test")
                .content("Sample1 content")
                .writer("user1")
                .build();
        long bno = boardService.register(boardDTO);
        log.info("bno : " + bno);
    }

    @Test
    public void testreadOne(){
        long bno = 101L;
        BoardDTO boardDTO = boardService.readOne(bno);
        log.info(boardDTO);
    }

    @Test
    public void testModify(){ //
        //변경에 필요한 데이터만 처리한다
       BoardDTO boardDTO = BoardDTO.builder()
               .bno(101L)
               .title("modify12")
               .content("modify1")
               .build();
       boardService.modify(boardDTO);
       log.info(boardService.readOne(101L));


    }
    @Test
    public void  testDelete(){ //


        long bno = 104L;
        boardService.remove(bno);
       Assertions.assertThrows(NoSuchElementException.class,
               () -> boardService.readOne(bno));

    }


    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tc")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<BoardDTO> responseDTO = boardService.list(pageRequestDTO);
        log.info(responseDTO);
    }



}
