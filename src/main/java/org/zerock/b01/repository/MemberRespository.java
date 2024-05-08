package org.zerock.b01.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.Member;

import java.util.Optional;

public interface MemberRespository extends JpaRepository<Member, String> {

        //P.723
    //일반 회원일경우 불러야하고 소셜일 경우에는 안된다.
    //소셜 자체가 외부에 있는애가 작업을 받아서 넘기면 내용으 처리한다.
    //있으면 있는대로 불러오고 없으면 만들어야 한다.
    //소셜 사용하지않는 사용자의 권환 로딩//조인을 위해서 사용한 어노테이션... ㅁ
    @EntityGraph(attributePaths = "roleSet")// false면 소셜 사용자 로그인이 아니라는것이다
    @Query("select m from Member m where m.mid = :mid and m.social = false")
    Optional<Member> getWithRoles (String mid);

    //p.753
    @EntityGraph(attributePaths = "roleSet")
    Optional<Member> findByEmail(String email);

    @Modifying //이 어노테이션을 사용하면 쿼리에서 DML 처리가 가능케 함 (insert/update/delete)
    @Transactional
    @Query("update Member m set m.mpw = :mpw where m.mid = :mid")
    void updatePassword(@Param("mpw")String password, @Param("mid") String mid );



}
