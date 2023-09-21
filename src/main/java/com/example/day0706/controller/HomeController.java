package com.example.day0706.controller;

import com.example.day0706.domain.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal Member member){
        return "/api/itemList/0";
    }
}
