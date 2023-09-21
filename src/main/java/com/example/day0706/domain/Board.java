package com.example.day0706.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity // 테이블과 연계
@Getter
@NoArgsConstructor
public class Board extends Timestamped{
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;
    @Column
    private String author;
    @Column
    private String title;
    @Column
    private String contents;

    @Column
    private String category;

    public Board(String author, String title , String contents,String category){
        this.author = author;
        this.title = title;
        this.contents = contents;
        this.category = category;
    }

    public Board(BoardRequestDto boardRequestDto){
        this.author  = boardRequestDto.getAuthor();
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
        this.category = boardRequestDto.getCategory();
    }

    public void update(BoardRequestDto boardRequestDto){
        this.author  = boardRequestDto.getAuthor();
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
        this.category = boardRequestDto.getCategory();
    }

}
