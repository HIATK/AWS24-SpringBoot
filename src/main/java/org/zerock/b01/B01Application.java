package org.zerock.b01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

//여기서 작업한건 나머지 파일들이 다 영향을 받는다
@SpringBootApplication  //Spring boot의 시작점을 나타내는 어노테이션이다.
@EnableJpaAuditing  //AuditingEnityListner 활성화 어노테이션
public class B01Application {

    public static void main(String[] args) {
        SpringApplication.run(B01Application.class, args);
    }


}
