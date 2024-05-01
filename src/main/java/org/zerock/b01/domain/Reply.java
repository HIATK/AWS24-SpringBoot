package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;

//domain은 엔터티다
@Entity
@Table(name = "Reply", indexes = {                  //게시글이 여러개 존재할수 있고 빠르게 불러오기위해 board_bno를 넣는다.

        @Index(name = "idx_reply_board_bno", columnList = "board_bno")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

//exclude == 제외하다
//Tostring을 할때 board를 빼달라는요청
//무한 순환을 일으킬 수 있기도 하고 이를 방지하기 위해서 한쪽에서 다른쪽의 참조를 제외한다.
@ToString//(exclude = "board") //참조하는 객체를 사용하지 않게 하기위해서 exclude로 board를 제외



//baseentity에 시간설정 수정시간이 들어가 있다
public class Reply extends BaseEntity {
    //키 생성규칙을 만드어야한다
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //AutoImplement
    private Long rno;

    //LAZY 천천히 느리게
    @ManyToOne(fetch = FetchType.LAZY)//다대일 관계로 구성됨(연관관계시 fetchtype은 Lazy로 구성한다)
    private Board board;               //board를 구분할 수 있는 pk인 bno가 들어간다.

    private String replyText;

    private String  replyer;



    //데이터 테이블 키에 MUL은 복합 이라는것이라고 생각하면 된다
    //foreignkey primarykey .

    //board값 설정을 위해서 ->BNO를 받아서 생성
//    public  void setBoard(Long bno){
//        this.board = Board.builder().bno(bno).build();
//    }


    public void changeText(String text){
        this.replyText = text;
    }


}
