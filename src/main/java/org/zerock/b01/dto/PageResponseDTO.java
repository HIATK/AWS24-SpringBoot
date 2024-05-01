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

    //1. pageRequestDTO , 2. DTO는  list , 3. total
    //빌더 = withAll 롬복의 어노테이션 빌드 , 빌더 메서드이름은 with올로 설정한다.
    @Builder(builderMethodName = "withAll")
    //생성자를 정의하고 페이지 리퀘스트 객체 E타입에 객체리스트 그리고 총 개수를 인자로 받는다.
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E>dtoList, int total){
        //총 개수가 0 이하인경우 생성자의 실행을 중단
        if(total <= 0){
            return;
        }
        //pageRequestDTO 객체에서 페이지번호, 사이즈를 가져와 필드에 설정한다.
        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();

        //dto리스트를 인자로받은 dtolist를 필드에 설정한다
        this.dtoList = dtoList;

        //페이지 번호를 10으로 나눈후 올림하여 10을 곱한값
        //이걸 end필드에 설정한다 이는 화면에 표시할 마지막 페이지 번호를 계산한다.
        this.end = (int)(Math.ceil(this.page / 10.0))*10;//화면에 표시할 페이지번호 갯수
        this.start = this.end -9; //화면에서 시작번호
        int last = (int)(Math.ceil(total/(double)size)); //데이터 개수로 계산한 마지막 페이지 번호


        this.end = end >last ? last :end ;

        this.prev = this.start >1;
        //총개수가 엔드 필드와 사이즈 필드의 곱보다 큰 경우 넥스트 필드를 트루로 설정한다
        //그렇지 않은 경우 넥스트 필드는 폴스로 설정된다.
        //이건 다음페이지가 있는지를 결정
        this.next = total > this.end * this.size; //페이지에 대한 내용을 같이 처리하려고하는것


    }


}
