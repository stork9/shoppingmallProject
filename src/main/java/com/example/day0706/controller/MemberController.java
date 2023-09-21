package com.example.day0706.controller;

import com.example.day0706.domain.*;
import com.example.day0706.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;


@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @PostMapping("/api/signup")
    public String signup(MemberRequestDto dto) { // 회원 추가
        memberService.save(dto);
        return "redirect:/api/login";
    }

    // 추가
    @GetMapping(value = "/user/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response, @AuthenticationPrincipal Member member) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        member = null;
        return "redirect:/api/login";
    }

    @GetMapping("/user/mypage")
    public ModelAndView mypage(@AuthenticationPrincipal Member member) {
        String userId = member.getUserId();
        String sample4_postcode = member.getSample4_postcode();
        String sample4_roadAddress = member.getSample4_roadAddress();
        String sample4_detailAddress = member.getSample4_detailAddress();
        String name = member.getName();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("mypage");
        mv.addObject("userId",userId);
        mv.addObject("name",name);
        mv.addObject("sample4_postcode",sample4_postcode);
        mv.addObject("sample4_roadAddress",sample4_roadAddress);
        mv.addObject("sample4_detailAddress",sample4_detailAddress);
        return mv;
    }

    @GetMapping("/user/profile")
    public ModelAndView profile(@AuthenticationPrincipal Member member) {
        Long member_id = member.getId();
        String userId = member.getUserId();
        String sample4_postcode = member.getSample4_postcode();
        String sample4_roadAddress = member.getSample4_roadAddress();
        String sample4_detailAddress = member.getSample4_detailAddress();
        String address = sample4_postcode+sample4_roadAddress + sample4_detailAddress;
        String name = member.getName();
        String email = member.getEmail();
        String phone = member.getPhone();


        Member profile_image = memberRepository.findByUserId(userId).orElseThrow(
                ()->new IllegalArgumentException("잘못된 접근입니다. 고객센터에 문의하세요.")
        );
        String orgNm = profile_image.getOrgNm();
        String savedNm = profile_image.getSavedNm();
        String savedPath = profile_image.getSavedPath();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("profile");
        mv.addObject("member_id",member_id);
        mv.addObject("userId",userId);
        mv.addObject("name",name);
        mv.addObject("sample4_postcode", sample4_postcode);
        mv.addObject("sample4_roadAddress", sample4_roadAddress);
        mv.addObject("sample4_detailAddress", sample4_detailAddress);
        mv.addObject("address",address);
        mv.addObject("email",email);
        mv.addObject("phone",phone);
        mv.addObject("orgNm",orgNm);
        mv.addObject("savedNm",savedNm);
        mv.addObject("savedPath",savedPath);
        return mv;
    }
    @DeleteMapping("/user/profile")
    public ResponseEntity<String> deleteMember(@AuthenticationPrincipal Member member) {
        Cart cart = cartRepository.findByMemberId(member.getId());
            List<CartItem>  cartItems = cartItemRepository.findByCartId(cart.getId());
            for(int i = 0; i < cartItems.size(); i++){
                cartItemRepository.deleteById(cartItems.get(i).getId());
            }
            cartRepository.deleteById(cart.getId());
            memberService.delete(member.getId());

        return ResponseEntity.ok("회원 탈퇴되었습니다.");
    }

    @GetMapping("/user/updatemember")
    public ModelAndView updateMemberPage(@AuthenticationPrincipal Member member) {
        String userId = member.getUserId();
        String sample4_postcode = member.getSample4_postcode();
        String sample4_roadAddress = member.getSample4_roadAddress();
        String sample4_detailAddress = member.getSample4_detailAddress();
        String address = sample4_postcode+sample4_roadAddress + sample4_detailAddress;
        String name = member.getName();
        String email = member.getEmail();
        String phone = member.getPhone();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("updatemember");
        mv.addObject("userId", userId);
        mv.addObject("name", name);
        mv.addObject("sample4_postcode", sample4_postcode);
        mv.addObject("sample4_roadAddress", sample4_roadAddress);
        mv.addObject("sample4_detailAddress", sample4_detailAddress);
        mv.addObject("address", address);
        mv.addObject("email", email);
        mv.addObject("phone", phone);
        mv.addObject("password", "");
        return mv;
    }

    @PostMapping("/user/updatemember")
    public String updateMember(@AuthenticationPrincipal Member member, MemberRequestDto dto) {
        memberService.update(member, dto);
        return "redirect:/user/mypage";
    }

    @GetMapping("/api/members/checkDuplicateId")
    @ResponseBody
    public Map<String, Boolean> checkDuplicateId(@RequestParam("userId")String userId) {
        Optional<Member> member = memberRepository.findByUserId(userId);
        boolean isDuplicate = member.isPresent();
        Map<String, Boolean> response = new HashMap<>();
        response.put("duplicate", isDuplicate);
        return response;
    }

    @RequestMapping("/api/signupPage")
    public String signupPage() { // 회원가입페이지
        return "signUp";
    }

    @RequestMapping("/api/accessory")
    public String accessory() {
        return "accessory";
    }

    @RequestMapping("/api/boardList")
    public String boardList() {
        return "boardList";
    }
    @RequestMapping("/api/bottom")
    public String bottom() {
        return "bottom";
    }
    @RequestMapping("/api/index")
    public String index() {return "top";}


    @RequestMapping("/api/login")
    public String login() {
        return "loginPage";
    }
    @RequestMapping("/user/orderHistory")
    public String orderHistory() {
        return "orderHistory";
    }




}


