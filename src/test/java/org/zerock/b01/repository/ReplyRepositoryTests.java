package org.zerock.b01.repository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.Reply;

import java.util.stream.IntStream;

@SpringBootTest
@Log4j2

public class ReplyRepositoryTests {
    @Autowired
    private ReplyRepository replyRepository;


    //테스트 : 있는 게시글 중에 댓글 추가...200번에 댓글 추가...(insert)
    @Test
    public void testInsert(){

        //실제 DB에 있는 bno
        Long bno = 191L;
        //엔티티에 보드가 보드객체를 얘기한다.
        //보드객체중에 bno를 불러서 사용한다.
        //프라이머리가 선언되어있는 보드객체가 필요하다

        Board board = Board.builder().bno(bno).build();
        //bno 는 빌더 객체에 bno 필드를 설정하는 메서드이다
        IntStream.range(1,100).forEach(i ->{
            Reply reply = Reply.builder()
                    .board(board)
                    .replyText("댓---"+i)
                    .replyer("replyer1"+i)
                    .build();
            replyRepository.save(reply);

        });
    }





    //쿼리가 둘다 성공해야 성공처리를 한다
    //처리가 완전히 끝날때까지 작업을 하지 않는다. 두가지 쿼리를 날려야한다...
    //결과론적으로 강제로 처리한다.
    @Transactional//두개다 성공할때까지 끝내지 않는다.

    @Test
    public void testBoardReplies(){
        //실제 게시물 번호 이번호는 테스트에 사용될 게시물의 id를 적은것이다
        Long bno = 191L;
        //sort.by에 rno가 들어간건 해당 비엔오에 인덱싱된걸 다시
        //첫 페이지의 10개 항목을 "rno" 필드를 기준으로 내림차순 정렬하라는 의미이다
        Pageable pageable =PageRequest.of(0,10,Sort.by("rno").descending());

        //replyRepository의 listOfBoard메서드를 호출하고 게시물에 해당하는 댓글을 페이지 단위로 가져온다
        //이 메서드는 게시물 번화와 pageable 객체를 인자로 받아 해당 게시물의 페이지 단위로 반환합니다
        Page<Reply> result = replyRepository.listOfBoard(bno,pageable);
        result.getContent().forEach(reply->{
            log.info(reply);
        });
    }


}
