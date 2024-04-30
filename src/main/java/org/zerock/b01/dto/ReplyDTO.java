package org.zerock.b01.dto;


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
    private long rno;
    private long bno;
    private String replyText;
    private String replyer;


    private LocalDateTime regDate, modDate;


}
