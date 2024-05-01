package org.zerock.b01.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ReplyDTO {
    //댓글 작업

    //자동생성

    private long rno;
    @NotNull//게시글번호
    private long bno;
    @NotEmpty //not null을 사용하지않은이유는 널과 empty는 다르다
    private String replyText;//응답내용

    //검증내용이 증명하면 익셉션 핸들러에서 예외처리를 시작한다
    @NotEmpty
    private String replyer;


    private LocalDateTime regDate, modDate;


}
