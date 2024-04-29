package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity //엔터티 선언
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
//Setter를 넣지 않은건 빌더로 작업을 하겠다는 뜻이다
//빌더로 객체생성...
public class Board extends BaseEntity {

    @Id
/*
* - IDENTIFY :데이터베이스에 위임(AUTO_INCREMENT)
* - SEQENCE : 데이터 베이스에 시퀀스 오브젝트를 사용 - @SequenceGenerator가 필요함...
* - TABLE:키 생성용 테이블 사용. 모든 DB에서 사용 - @TableGenerator 필요함
* - AUTO : 방언에 따라서 자동 지정됨. 기본값
*  - UUID : 8글자 4글자 4글자 16진수 (128비트)
*/
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  자동으로 값을 생성해주겠다는 뜼이다.
    private Long bno;

    @Column(length = 500, nullable = false) // 컴럼의 길이와 null의 허용여부(Not Null)
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    @Column(length = 50,nullable = false)
    private String writer;

    //title 과 content는 변경이 가능 할수도있다.
    //title과 content를 수정하기 위한 메서드다.
    //여기서 제일 중요한건 아이디다 아이디는 변경되면안된다.
    //엔티티 내에서 변경가능한건 타이틀과 콘텐트다.
    public void change(String title, String content){
        this.title = title;
        this.content = content;
    }



}
