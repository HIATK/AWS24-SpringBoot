package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

        //P.723
    //일반 회원일경우 불러야하고 소셜일 경우에는 안된다.
    //소셜 자체가 외부에 있는애가 작업을 받아서 넘기면 내용으 처리한다.
    //있으면 있는대로 불러오고 없으면 만들어야 한다.
    //소셜 사용하지않는 사용자의 권환 로딩//조인을 위해서 사용한 어노테이션... ㅁ
    @EntityGraph(attributePaths = "roleSet")// false면 소셜 사용자 로그인이 아니라는것이다
    @Query("select m from Member m where m.mid = :mid and m.social = false")
    Optional<Member> getWithRoles (String mid);




}
