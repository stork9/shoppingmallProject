package com.example.day0706.service;

import com.example.day0706.domain.Member;
import com.example.day0706.domain.MemberRepository;
import com.example.day0706.domain.MemberRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class FileService {

    @Value("${file.dir}")
    private String fileDir;

    private final MemberRepository memberRepository;

    @Transactional
    public String saveFile(String userId, MultipartFile files) throws IOException {
        if (files.isEmpty()) {
            return null;
        }

        // 원래 파일 이름 추출
        String origName = files.getOriginalFilename();

        // 파일 이름으로 쓸 uuid 생성
        //String uuid = UUID.randomUUID().toString();

        // 확장자 추출(ex : .png)
        String extension = origName.substring(origName.lastIndexOf("."));

        // uuid와 확장자 결합
        String savedName = origName; //+ extension;

        // 파일을 불러올 때 사용할 파일 경로
        String savedPath = fileDir + savedName;

        Member member = memberRepository.findByUserId(userId).orElseThrow(
                ()->new IllegalArgumentException("잘못된 접근입니다. 고객센터에 문의하세요.")
        );
        // 파일 엔티티 생성
        /*Member file = Member.builder()
                .orgNm(origName)
                .savedNm(savedName)
                .savedPath(savedPath)
                .build();
*/
        MemberRequestDto testdto = new MemberRequestDto();

        member.setOrgNm(origName);
        member.setSavedNm(savedName);
        member.setSavedPath(savedPath);

        memberRepository.save(member);

        // 실제로 로컬에 uuid를 파일명으로 저장
        files.transferTo(new File(savedPath));

        // 데이터베이스에 파일 정보 저장
        testdto.setOrgNm(origName);
        testdto.setSavedNm(savedName);
        testdto.setSavedPath(savedPath);
        member.img_update(userId, member);
        //Member savedFile = memberRepository.save(file); (무시)
        return member.getUserId();
    }
}