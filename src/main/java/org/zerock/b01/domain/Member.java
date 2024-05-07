package org.zerock.b01.domain;


import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "roleset")
public class Member extends BaseEntity{

    //p.720
    //primaryKey
    // 사용자 아이디
    @Id
    private String mid;
    // 패스워드
    private String mpw;
    // 소셜로그인
    private String email;

    //삭제 여부
    private boolean del;

    //열거형 처리 ...roleset


    //열거형 대상만 되고 나머지는 안된다.
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    //소셜의 정보를 받아와서 그 소셜에 있는.... 통신해서 그 결과를 받아온다.
    private boolean social;

    //이메일  변경
    //JPA는 persistance 내에 들어간다
    public void  changeEmail(String email){
        this.email=email;
    }


    //패스워드 변경
    public void changePassword(String mpw){
        this.mpw = mpw;
    }

    //삭제 여부 변경
    public void changeDel(boolean del){
        this.del = del;
    }

    //addRole 역할 추가
    public void addRole(MemberRole memberRole){
        this.roleSet.add(memberRole);
    }

    //역할 삭제를 위해서
    public void clearRoles(){
        this.roleSet.clear();
    }


    // 소셜여부 변경을 위해서 ...
    public void changeSocial(boolean social){
        this.social = social;
    }






}
