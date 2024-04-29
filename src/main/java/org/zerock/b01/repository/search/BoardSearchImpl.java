package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.Board;
import org.zerock.b01.domain.QBoard;

import java.util.List;


/*
 *  BoardSearch를 상속받는 구현제
 */
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class); //쿼리 dsl 서퍼트에게 넘기는 것이다
    }


    //Q도메인 연결 및 작성 교과서 451쪽
    @Override
    public Page<Board> search1(Pageable pageable) {

        //Q도메인을 이용한 쿼리 작성 및 테스트
        //Querydsl의 목적은 "타입" 기반으로 "코드"를 이용해서 JPQL 쿼리를 생성하고 실행한다...
        //Q도메인은 이 때에 코드를 만드는 대신에 클래스가 Q도메인 클래스...

        //Q도메인 객체 생성
        QBoard board = QBoard.board; //엔터티에 있는 걸 지정 함




        JPQLQuery<Board> query = from(board);//select.. from board
        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        query.where(board.title.contains("1")); //where title

        //7교시 BooleanBuilder() 사용-------------------------------------------------
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        booleanBuilder.or(board.title.contains("11"));  //title like -
        booleanBuilder.or(board.content.contains("11"));    // Content like - (11을 가지고 있는가)

        query.where(booleanBuilder); //where title like -

        query.where(board.bno.gt(0L)); //gt 뜻 00이상 00 보다 크다 라는 뜻이다
        //7교시 BooleanBuilder() 사용끝-------------------------------------------------

        List<Board> title = query.fetch(); //JPQL 쿼리에 대한 실행문

        long count = query.fetchCount();//fetch카운트를 이용해서 쿼리를 실행한다.

        return null;
    }



    // 26/7교시 title과 content 의 내용을 검색 ...
    @Override
    public Page<Board> searchAll(String[] types, String keyword, Pageable pageable) {
        //1.Qdomain 객체 생성
        QBoard board = QBoard.board;

        // 2. QL 작성 ..
        JPQLQuery<Board> query = from(board); //select ... from board

        if(( types != null && types.length > 0 && keyword != null )){
            //검색 조건과 키워드가 있는 경우.....

            BooleanBuilder booleanBuilder = new BooleanBuilder(); //(

            for(String type: types){
                switch (type){
                    case "t":
                        booleanBuilder.or(board.title.contains(keyword)); // title like  concat ('%'keyword'%')
                        break;
                    case  "c":
                        booleanBuilder.or(board.title.contains(keyword)); //content like  concat ('%'keyword'%')
                        break;
                    case  "w":
                        booleanBuilder.or(board.title.contains(keyword));//writer  like  concat ('%'keyword'%')
                        break;
                }
            } //for end
            query.where(booleanBuilder); //)

        }//if end

        query.where(board.bno.gt(0L)); //gt 0보다 커야한다.

        //paging
        this.getQuerydsl().applyPagination(pageable, query);//이건 limit 가 들어간거와 같다

        List<Board> list = query.fetch();
        long count = query.fetchCount();

        //page<T>형식으로 반환 : Page<Board>
        //page반환은 pageImpl을 사용해서 반환 : (list  - 실제 목록 데이터, pageable, total - 전체 개수)
        return new PageImpl<>(list,pageable,count);

    }
    // 26/7교시 title과 content 의 내용을 검색끝 ...
}
