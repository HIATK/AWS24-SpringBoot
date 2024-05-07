package org.zerock.b01.controller.advice;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.MemberJoinDTO;
import org.zerock.b01.service.MemberService;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    //의존성 주입.. 느슨한 연결
    private final MemberService memberService;

    @GetMapping("/login")
    public void loginGET(String error,String logout){
        log.info("login get ...");
        log.info("logout : " + logout);

        if(logout != null){
            log.info("user logout......");
        }
    }

    @GetMapping("/join")
    public void joinGET(){
        log.info(" - - - - - - J O I N  - - - - - ");

    }
    @PostMapping("/join")
    public String joinPost(MemberJoinDTO memberJoinDTO, RedirectAttributes redirectAttributes){

        log.info(" - - - - - J O I N - P O S T  - - - - - - ");
        log.info(memberJoinDTO);

        try {
            memberService.join(memberJoinDTO);
        }catch (MemberService.MidExistException e){
            redirectAttributes.addFlashAttribute("error","mid");
            return "redirect:/member/join";
        }

        log.info(" - - - - - J O I N - P O S T   END- - - - - - ");

        redirectAttributes.addFlashAttribute("result","success");

        return "redirect:/member/login"; //회원 가입 후  로그인처리
    }




}
