package org.zerock.b01.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

//공통작업은 베이스엔터티로 뺀다
/*
 * baseEntity에 설정할 내용 모든 테이블에서 공통적으로 사용하는 컬럼을 작성
 * @MappedSuperClass 를 사용하여 공통속성 적용 !!!
 * 이를 통해서 공통으로 사용되는 컬럼을 지정하여 해당 클래스를 상송하여 사용할 수 있음.
 *
 */
@MappedSuperclass
@EntityListeners(value = {AuditingEntityListener.class})
@Getter

abstract class BaseEntity{

    //시간 설정 - 등록, 수정 시간 설정 ..
    // 생성 시간 설정.
    @CreatedDate //생성날자 regidate now랑 비슷하다
    @Column(name = "regdate", updatable = false)   //수정불가능으로 설정
    private LocalDateTime regDate;


    //
    @LastModifiedDate
    @Column(name = "moddate")
    private LocalDateTime modDate;


}
