package com.example.day0706.service;

import com.example.day0706.domain.Member;
import com.example.day0706.domain.MemberRepository;
import com.example.day0706.domain.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    /**
     * Spring Security 필수 메소드 구현
     *
     * @param userId 이메일
     * @return UserDetails
     * @throws UsernameNotFoundException 유저가 없을 때 예외 발생
     */
    @Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
    public Member loadUserByUsername(String userId) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException((userId)));
    }

    /**
     * 회원정보 저장
     *
     * @param dto 회원정보가 들어있는 DTO
     * @return 저장되는 회원의 PK
     */
    public Long save(MemberRequestDto dto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        dto.setPassword(encoder.encode(dto.getPassword()));

        return memberRepository.save(Member.builder()
                        .phone(dto.getPhone())
                        .name(dto.getName())
                        .sample4_detailAddress(dto.getSample4_detailAddress())
                        .sample4_roadAddress(dto.getSample4_roadAddress())
                        .sample4_postcode(dto.getSample4_postcode())
                        .passwordCheck(dto.getPasswordCheck())
                        .email(dto.getEmail())
                        .auth(dto.getAuth())
                        .userId(dto.getUserId())
                        .password(dto.getPassword()).build()).getId();
    }

    public void update(Member member, MemberRequestDto dto) {
        member.setName(dto.getName());
        member.setPhone(dto.getPhone());
        member.setEmail(dto.getEmail());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        dto.setPassword(encoder.encode(dto.getPassword()));
        member.setPassword(dto.getPassword());
        member.setPasswordCheck(dto.getPasswordCheck());
        member.setSample4_postcode(dto.getSample4_postcode());
        member.setSample4_roadAddress(dto.getSample4_roadAddress());
        member.setSample4_detailAddress(dto.getSample4_detailAddress());

        member.setOrgNm(dto.getOrgNm());
        member.setSavedNm(dto.getSavedNm());
        member.setSavedPath(dto.getSavedPath());


        memberRepository.save(member);
    }

    public void delete(Long userId) {
        memberRepository.deleteById(userId);
    }

    public Member findUser(Long id){
        return memberRepository.findById(id).get();
    }
}
