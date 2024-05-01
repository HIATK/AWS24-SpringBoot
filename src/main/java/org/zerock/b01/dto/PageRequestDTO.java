package org.zerock.b01.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.eclipse.jdt.internal.compiler.util.Util.UTF_8;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PageRequestDTO {

    @Builder.Default
    private int page = 1;

    @Builder.Default
    private  int size =10;

    private String type;    //검색타입 종류 : t, c, w, tc, tw, twc,

    private String keyword;
    //배열 타입으로 받아온후 처리
    public String[] getTypes(){
        //만약 타입이 널또는 비었다면
        if(type == null || type.isEmpty()){
            return null; //널 객체를 반환한다
        }
        //스플릿으로 한글자씩 쪼갬
        return type.split(""); //한글자 한글자 다 나눔
    }


    public Pageable getPageble(String...props){
        return PageRequest.of(this.page-1,this.size, Sort.by(props).descending());
    }

    private  String link;

    public String getLink(){
        //스트링 빌더는 빌더다
        StringBuilder builder = new StringBuilder();
        //페이지= 제공된 페이지면 빌더에 추가한다
        builder.append("page="+this.page);
        //사이즈도 빌더에 추가
        builder.append("&size = "+this.size);
        //만약 타입이 널이고 타입의 길이가 0보다 길다면
        if( type !=null && type.length() >0){
            //타입도 빌더에 추가한다.
            builder.append("&type = "+type);
        }
        //만약 키워드가 널이아니라면
        if(keyword != null){
            //예외처리를 한다
            try {
                builder.append("&keyword="+ URLEncoder.encode(keyword, "UTF-8"));
            }catch (UnsupportedEncodingException e){

            }
        }
        link = builder.toString();

        return link;
    }

}
