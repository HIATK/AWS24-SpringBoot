package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Reply;
//특정한 게시글의 댓글들은 페이징 처리를 할 수 있도록 pageable기능을 RepleyRepository에 @Query를 이용해서 작성한다.
public interface ReplyRepository  extends JpaRepository<Reply,Long> {

    //JPA SQL 언어
    @Query("select r from  Reply r where  r.board.bno = :bno")
    //검색 조건이 빨라진다 안그러면 하나하나 비교를 해야함
    Page<Reply> listOfBoard(Long bno, Pageable pageable);



}
