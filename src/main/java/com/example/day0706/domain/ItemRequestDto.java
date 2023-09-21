package com.example.day0706.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@AllArgsConstructor
public class ItemRequestDto {
    private String title;
    private String desc;
    private String shortDesc;
    private String category;
    private int price;
    private MultipartFile uploadFile;
}
