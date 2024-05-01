package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.Board;
import org.zerock.b01.dto.BoardListReplyCountDTO;


import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.aspectj.apache.bcel.Constants.types;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {

    @Autowired
    private BoardRepository boardRepository;

    //insert
    @Test
    public void testInsert(){
        IntStream.rangeClosed(1,100).forEach(i ->{  //1번 게시물부터 100번게시물까지 집어넣는다는 의미
            Board board = Board.builder()
                    .title("title..."+i)
                    .content("content..."+i)
                    .writer("user"+(i%10))//사용자는 0번부터 9번까지
                    .build();
            Board result =boardRepository.save(board);
            log.info("BNO"+result.getBno());
        });

    }

    @Test   //select 문
    public void testSelect(){
        Long bno = 100L;

        //Long으로 되어있음 id로 검색하도 록 되어있음 제너릭 <t,id> 중에서 아이디가 들어간다.
        //반환타입은 옵셔널 타입이다.
        Optional<Board> result = boardRepository.findById(bno);

        //옵셔널에 메서드들이 존재함.
        //orElseThorw는 밸류값이 없다면 throws 예외발생 시킨다. noSearchElementException 시킴
        Board board = result.orElseThrow();

        log.info(board);

    }
    //Entiry는 생성시 불변이면 좋으나, 변경이 일어날 경우 최소한으로 설계...
    @Test ///update 문 동일한 아이다가 없으면 인서트, 동일한 아이디가 있으면 인서트 한다.
    public void testUpdate(){
        Long bno = 100L;
        Optional<Board> result = boardRepository.findById(bno);
        Board board = result.orElseThrow();
        board.change("update..title100","update content---100");

        boardRepository.save(board);
    }

    //삭제하기
    @Test
    public void testDelete(){
        Long bno = 1L;

        boardRepository.deleteById(bno);
    }

    //pageable Page<E> 타입을 이용한 페이징 처리
    //페이지 처리는 Pageable이라는 타입의 객체를 구성해서 파라미터로 전달...
    //Pageable 은 인터페이스로 설계되어 있고, 일반적으로 PageRequest.of()를 이용해서 개발함.
    //PageRequest.of(페이지번호, 사이즈): 페이지번호는 0번부터...
    //PageRequest.of(페이지번호,사이즈,Sort.Direction,속성):Sort 객체를 통한 정렬 조건을 추가함

    // Pageable 값으로 값을 넘기면 반환 타입은 Page<T >를 이용하게 된다.
    @Test
    public void testPaging(){
        //1 page order by title desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by("title").descending());

        Page<Board> result = boardRepository.findAll(pageable);

        //total 카운트를 사용하는 이유 :
        log.info("total count : "+ result.getTotalElements()); // 페이지 카운트
        log.info("total page : "+result.getTotalPages()); // 총 페이지
        log.info("page number : "+result.getNumber()); //페이지 번호
        log.info("page size : "+result.getSize()); //페이지 사이즈
        log.info("다음 페이지 여부? : "+result.hasNext());
        log.info("이전 페이지 여부? : "+result.hasPrevious());

        List<Board> boardList=  result.getContent();
        boardList.forEach(board -> log.info(board));

    }

    //쿼리 메서드 밎 @Query 테스트
    @Test
    public void testQueryMethod(){

        Pageable pageable = PageRequest.of(0,10);

        String keyword = "title";

        Page<Board> result = boardRepository.findByTitleContainingOrderByBnoDesc(
                keyword,
                pageable
        );
        result.getContent().forEach(board -> log.info(board));
    }

    @Test //정확한걸
    public void testQueryAnnotaion(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

        Page<Board> result = boardRepository.findKeyword("title",pageable);
        result.getContent().forEach(board -> log.info(board));

    }


    @Test
    public void testGetTime(){
        log.info(boardRepository.getTime());
    }


    //TEST 교과서 453p
    @Test
    public void testSearch1(){
        //2 page order by desc
        Pageable pageable = PageRequest.of(1,10,Sort.by("bno"));
        boardRepository.search1(pageable);
    }

    //TEST교과서 457
    @Test
    public void testSearchAll(){
        String[] types = {"t","c","w"};

        String keyword ="1";

        Pageable pageable = PageRequest.of(1,10,Sort.by("bno").descending());

        Page<Board> result = boardRepository.searchAll(types,keyword,pageable);

        result.getContent().forEach(board -> log.info(board));
        log.info("사이즈 : "+result.getSize());
        log.info("페이지 번호 : "+result.getNumber());
        log.info("이전 페이지 : "+result.hasPrevious());
        log.info("다음 페이지 : "+result.hasNext());

        boardRepository.searchAll(types,keyword,pageable);
    }

    @Test
    public void testSearchWithReplyCount(){
    String[] types = {"t","c","w"};

    String keyword ="87";

    Pageable pageable = PageRequest.of(0,10,Sort.by("bno").descending());

     Page<BoardListReplyCountDTO> result = boardRepository.searchWithReplyCount(types,keyword,pageable);
     result.getContent().forEach(boardListReplyCountDTO -> {
         log.info(boardListReplyCountDTO);
     });
    }

}

