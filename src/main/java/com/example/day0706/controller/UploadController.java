package com.example.day0706.controller;

import com.example.day0706.domain.Member;
import com.example.day0706.domain.MemberRepository;
import com.example.day0706.service.FileService;
import com.example.day0706.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UploadController {
    private final FileService fileService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/img_upload/{userId}")
    public String testUploadForm() {

        return "user/profile";
    }

    @PostMapping("/img_upload/{userId}")
    public String uploadFile(@PathVariable String userId, @RequestParam("file") MultipartFile file) throws IOException {
        fileService.saveFile(userId, file);


        return "redirect:/user/profile";
    }

}
