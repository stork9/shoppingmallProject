package com.example.day0706.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BoardRequestDto {
    private String author;
    private String title;
    private String contents;
    private String category;
}
