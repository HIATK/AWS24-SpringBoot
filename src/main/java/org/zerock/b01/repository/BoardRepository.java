package org.zerock.b01.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.zerock.b01.domain.Board;
import org.zerock.b01.repository.search.BoardSearch;


public interface BoardRepository extends JpaRepository<Board,Long>, BoardSearch {


    //한개면 옵셔널
    Page<Board> findByTitleContainingOrderByBnoDesc(String keyword, Pageable pageable);

    //@Quary 어노테이션 에서 사용하는 구문은 JPQL을 이용한다 (sql이 아니다 )d
    //JPQL은 SQL과 유사하게 JPA에서 사용하는 쿼리 언어.
    //jpa에 있는 보드 엔터티를 얘기한다
    //Query를 이용하는 경우..
    //1. 조인과 같이 복잡한 쿼리를 실행하려고 할때
    //2. 원하는 속성만 추출해서 Object[]로 처리하거나 DTO로 처리가 가능
    //3. 속성 값 중 nativeQuery 값은 true로 지정하면 SQL구문으로 사용이 가능하다.
    @Query("select b from Board b where b.title like concat('%',:keyword,'%') ")
    Page<Board> findKeyword(String keyword, Pageable pageable);

    //native로 사용하는 방법
    @Query(value = "select now()", nativeQuery = true)
    String getTime();

}
