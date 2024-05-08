package org.zerock.b01.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.type.UserComponentType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

///스프링 시큐리티에는 UserDetails라는 인터페이스를 사용한다.
// 때문에 스프링 시큐리티에서 userDetail의 구현체인 User를 상속받아 구현한다

@Getter
@Setter
@ToString
public class MemberSecurityDTO extends User implements OAuth2User {

    private String mid;
    private String mpw;
    private String email;
    private boolean del;
    private boolean social;
    //p.754
    private Map<String,Object> props; //소셜 로그인 정보

    public MemberSecurityDTO(String username, String password, String email,boolean del, boolean social,
                             Collection<? extends GrantedAuthority> authorities) {

        //user에 있는 기본 name,pw,권환
        super(username,password,authorities);
        // 부가적인 요소를 추가하여 넘겨준다.
        this.mid = username; //
        this.mpw =password;
        this.email =email;
        this.del = del;
        this.social = social;

    }

        //P.754 impl 하고 생성됨

    @Override
    public Map<String, Object> getAttributes() {
        return this.getProps();
    }

    @Override
    public String getName() {
        return this.mid;
    }
}
