package org.zerock.b01.service;

import org.zerock.b01.dto.MemberJoinDTO;

public interface MemberService {
    //일종의 상수값과 비슷하다
    static class MidExistException extends Exception{

    public  MidExistException(){}

        //오버로드
    public MidExistException(String msg){
        super(msg);
    }

    }



    //아이디가중복되면안된다  중복된 아이디가 있으면 예외처리를 시켜주고 나가야한다
    //문제가 없으면 회원가입을 시킨다.
    void join(MemberJoinDTO memberJoinDTO) throws MidExistException;


}
