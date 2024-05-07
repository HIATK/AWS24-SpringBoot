package org.zerock.b01.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@Getter
@Setter
@ToString
public class MemberJoinDTO {

    private String mid;
    private  String mpw;
    private String email;
    private boolean del;
    private boolean social;



}
