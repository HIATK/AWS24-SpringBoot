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

    public String[] getTypes(){
        if(type == null || type.isEmpty()){
            return null;
        }
        return type.split(""); //한글자 한글자 다 나눔
    }
    public Pageable getPageble(String...props){
        return PageRequest.of(this.page-1,this.size, Sort.by(props).descending());
    }

    private  String link;

    public String getLink(){
        StringBuilder builder = new StringBuilder();
        builder.append("page="+this.page);
        builder.append("&size = "+this.size);
        if( type !=null && type.length() >0){
            builder.append("&type = "+type);
        }
        if(keyword != null){
            try {
                builder.append("&keyword="+ URLEncoder.encode(keyword, "UTF-8"));
            }catch (UnsupportedEncodingException e){

            }
        }
        link = builder.toString();

        return link;
    }

}
