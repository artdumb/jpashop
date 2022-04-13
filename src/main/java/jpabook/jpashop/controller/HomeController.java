package jpabook.jpashop.controller;


import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j // 로깅을 위한 slf4j의 객체생성 대신해주는 롬복 어노테이션
public class HomeController {

    //Logger log = LoggerFactory.getLogger(getClass()); (Slf4j로 입포트해야함)

    @RequestMapping("/")
    public String home(){
        log.info("home controller");//log
        return "home";
    }
}
