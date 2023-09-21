package com.example.day0706.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.*;

@Setter
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends Timestamped implements UserDetails {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="member_id")
    private Long id;
    @Column(name="userId", unique = true)
    private String userId;
    @Column(name="password")
    private String password;
    @Column(name="passwordCheck")
    private String passwordCheck;
    @Column(name="name")
    private String name;
    @Column(name = "auth")
    private String auth; //권한
    @Column(name="phone")
    private String phone; //유효성검사에서 Phone 일 수 있으니 재확인
    @Column(name="email")
    private String email;
    @Column(name="sample4_postcode")
    private String sample4_postcode; //기존 변수명: //우편번호
    @Column(name="sample4_roadAddress")
    private String sample4_roadAddress; //기존 변수명: //도로명주소
    @Column(name="sample4_detailAddress")
    private String sample4_detailAddress; //기존 변수명: //상세주소
    @Column(name="orgNm")
    private String orgNm;
    @Column(name="savedNm")
    private String savedNm;
    @Column(name="savedPath")
    private String savedPath;

    @Builder
    public Member(String userId, String password, String passwordCheck,
                  String name,String auth, String phone, String email, String sample4_postcode, String sample4_roadAddress,
                  String sample4_detailAddress){
        this.userId = userId;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.name = name;
        this.auth = auth;
        this.phone = phone;
        this.email = email;
        this.sample4_postcode = sample4_postcode;
        this.sample4_roadAddress = sample4_roadAddress;
        this.sample4_detailAddress = sample4_detailAddress;
        this.orgNm = orgNm;
        this.savedNm = savedNm;
        this.savedPath = savedPath;
    }

    // 사용자의 권한을 콜렉션 형태로 반환
    // 단, 클래스 자료형은 GrantedAuthority를 구현해야함
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    // 사용자의 id를 반환 (unique한 값)
    @Override
    public String getUsername() {
        return userId;
    }

    // 사용자의 password를 반환
    @Override
    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return sample4_postcode + sample4_roadAddress + sample4_detailAddress ;
    }
    public String getEmail(){
        return email;
    }
    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
    @Transactional
    public void img_update(String userId, Member member){

        this.orgNm = member.getOrgNm();
        this.savedNm = member.getSavedNm();
        this.savedPath = member.getSavedPath();

    }


}
