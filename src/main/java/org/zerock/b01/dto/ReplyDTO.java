package org.zerock.b01.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data //getter, setter ,equals,hashcode, toSTring 메서드 자동생성
@Builder //빌더 패턴 클래스 생성
@AllArgsConstructor //모든 값은 파라미터로 받는 생성자를 생성 함
@NoArgsConstructor //라마미터가 없는 기본생성자를 생성

public class ReplyDTO {
    //댓글 작업

    //자동생성

    private long rno; //댓글번호 필드
    @NotNull//게시글번호
    private long bno; //게시글 번호 필드
    @NotEmpty //not null을 사용하지않은이유는 널과 empty는 다르다
    private String replyText;// 댓글 내용 필드

    //검증내용이 증명하면 익셉션 핸들러에서 예외처리를 시작한다
    @NotEmpty
    private String replyer; //댓글 작성자


    //시간 포맷 설정
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    //JSON 변환 시 무시하겠다는 어노테이션
    @JsonIgnore
    private LocalDateTime modDate;


}
