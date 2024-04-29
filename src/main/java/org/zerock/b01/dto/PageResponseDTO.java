package org.zerock.b01.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

//불러오는 작업만 한다.
@Getter
@ToString
public class PageResponseDTO<E> {

    private int page;
    private  int size;
    private int total;
    //

    //시작페이지 번호
    private int start;
    //끝페이지 번호
    private int end;

    //이전페이지 여부
    private boolean prev;
    //다음페이지 여부
    private boolean next;

    private List<E>dtoList; //게시글 내용!! 기본생성자를 만들지 않는다.

    //1. pageRequestDTO , 2. DTO list , 3. total
    @Builder(builderMethodName = "withAll")
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E>dtoList, int total){
        if(total <= 0){
            return;
        }
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        this.dtoList = dtoList;

        this.end = (int)(Math.ceil(this.page / 10.0))*10;//화면에 표시할 페이지번호 갯수
        this.start = this.end -9; //화면에서 시작번호
        int last = (int)(Math.ceil(total/(double)size)); //데이터 개수로 계산한 마지막 페이지 번호


        this.end = end >last ? last :end ;

        this.prev = this.start >1;
        this.next = total > this.end * this.size; //페이지에 대한 내용을 같이 처리하려고하는것


    }


}
