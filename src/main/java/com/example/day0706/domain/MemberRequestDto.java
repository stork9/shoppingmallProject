package com.example.day0706.domain;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberRequestDto extends Timestamped{
    private String userId;
    private String password;
    private String passwordCheck;
    private String name;
    private String auth; //권한
    private String phone; //유효성검사에서 Phone 일 수 있으니 재확인
    private String email;
    private String sample4_postcode; //기존 변수명:sample4_postcode //우편번호
    private String sample4_roadAddress; //기존 변수명:sample4_roadAddress //도로명주소
    private String sample4_detailAddress; //기존 변수명:sample4_detailAddress //상세주소
    private String orgNm;
    private String savedNm;
    private String savedPath;
}
