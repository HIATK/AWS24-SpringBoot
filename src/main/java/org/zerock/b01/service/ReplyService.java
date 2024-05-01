package org.zerock.b01.service;

import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;

public interface ReplyService {

    Long register(ReplyDTO replyDTO);

    ReplyDTO read(Long rno);

    void modify(ReplyDTO replyDTO);

    void remove (Long rno);

    //특정 게시물의 댓글 목록 불러오기
    //게시물의 값을 전달받아서 원하는 게시물의 정보를 읽는다.
    PageResponseDTO<ReplyDTO> getListOfBOard(Long bno, PageRequestDTO pageRequestDTO);


}
