package org.zerock.b01.controller;


import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.ReplyDTO;
import org.zerock.b01.service.ReplyService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;  //의존성 주입

    @Operation(summary = "Replies Post - Post방식으로 댓글 등록")
    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(
            @Valid @RequestBody ReplyDTO replyDTO,
            BindingResult bindingResult) throws BindException {
        log.info(replyDTO);

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = new HashMap<>();
        Long rno = replyService.register(replyDTO);

        resultMap.put("rno", rno);

        return resultMap;
    }


    // 특정  게시물의 댓글 목록 보기...
    // URI -> '/replies/list/{bno}' , 방식 -> GET
    // @PathVariable 어노테이션 사용!
    @Operation(summary = "Replies of Board로 GET방식으로 특정 게시물 댓글 목록 처리...")
    @GetMapping(value = "/list/{bno}")
    public PageResponseDTO<ReplyDTO> getList(
            @PathVariable("bno") Long bno,
            PageRequestDTO pageRequestDTO) {

        PageResponseDTO<ReplyDTO> responseDTO = replyService.getListOfBOard(bno,pageRequestDTO);

        return responseDTO;
    }
    //Swagger에서 보여질 API를 설명하는 어노테이션이다
    @Operation(summary = "Read Reply - GET방식으로 댓글 조회")
    //겟 매핑
    @GetMapping("/{rno}") //url의 일부로 들어오는 값을 변수로 사용할수 있게 해준다
    //rno를 파라미터로 받아서 댓글을 조회한다.
    public ReplyDTO getReplyDTO(@PathVariable("rno") Long rno) {
        //리플라이 서비르를 읽는다 rno = replyDTO
        ReplyDTO replyDTO = replyService.read(rno);
        //  그걸 반환해준다
        return replyDTO;
    }

    //삭제 메서드
    //swagger에 api에 보여줄 메세지
    @Operation(summary = "Delete Reply - DELETE 메서드를 이용한 댓글 삭제")
    //deleteMpping, putMapping 이 있다
    ///딜리트 매핑은 삭제를 한다는 어노테이션이다
    @DeleteMapping("/{rno}")
    //Map에 삭제한 댓글의 번호를 담는다
    public Map<String, Long> remove(@PathVariable("rno") Long rno) {
        //리플라이 서비스에 리무브에 rno를 넣는다
        replyService.remove(rno);
        //삭제한 댓글의 번호를 담을 Map을 생성한다
        //키는 String , Long은 값의 타입이다  이코드에서 댓글의 번호 rno를 롱 타입으로 지정하고
        //이를 rno라는 String타입의 키와 연결해 Map에 저장하고 있다 이렇게하면 rno라는 키를 통해 댓글의 번호를 쉽게 찾아낼수 있다
        //이런 방식은 API응답으로 JSON형태의 데이터를 보낼때 유용하다 Map은 JSON객체로 변환될때 키와 값이 쌍으로
        //제이슨의 필드와 값의 쌍으로 변환되기 때문이다
        Map<String, Long> resultMap = new HashMap<>();
        //삭제할 댓글의 번호를 맵에 추가수

        resultMap.put("rno", rno);
//      삭제한 댓글의 번로를 담은 Map을 반환한다.
        return resultMap;
    }

    //Swagger UI에서 보여질 API를 설명
    @Operation(summary = "Modify Reply - PUT 방식으로 댓글 수정")
    //HTTP PUT요청을 처리한다
    @PutMapping(value = "/{rno}", consumes = MediaType.APPLICATION_JSON_VALUE)
    //
    public Map<String, Long> modify(
            //경로 변수 rno를 파라미터로 받고
            @PathVariable("rno") Long rno,
            //요청 본문을 Reploy 객체로 반환하여 파라미터로 받는다
            @RequestBody ReplyDTO replyDTO){
        //댓글의 게시글 번호를 경로 변수로 지정
        replyDTO.setBno(rno);   // 번호 일치
        //댓글 서비스를통해 댓글을 수정
        replyService.modify(replyDTO);
        //수정한 댓글의 번호를 담을 Map을 생성한다
        Map<String, Long> resultMap = new HashMap<>();
        //수정한 댓글의 번호를 Map에 추가
        resultMap.put("rno", rno);
        return resultMap;
    }
}