package com.example.day0706.controller;


import com.example.day0706.domain.Board;
import com.example.day0706.domain.BoardRepository;
import com.example.day0706.domain.BoardRequestDto;
import com.example.day0706.domain.Member;
import com.example.day0706.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;

    @PostMapping("/admin/boards")//생성
    public Board createMemo(@RequestBody BoardRequestDto requestDto){
        Board board = new Board(requestDto);
        return boardRepository.save(board);
    }

    @GetMapping("/api/boards")//전체출력
    public List<Board> getMemo(){
        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now();
        return boardRepository.findAllByModifiedAtBetweenOrderByModifiedAtDesc(start , end);
    }

    @DeleteMapping("/admin/boards/{id}")
    public Long deleteBoard(@PathVariable Long id){
        boardRepository.deleteById(id);
        return id;
    }

    @PutMapping("/admin/boards/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto){
        boardService.update(id, requestDto);
        return id;
    }

    @GetMapping("/api/boards/{id}")
    public ModelAndView boardOne(@PathVariable Long id, ModelAndView model, @AuthenticationPrincipal Member member){
        Board board = boardRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 아이디는 존재하지 않습니다.")
        );
            model.setViewName("boardOne");
            model.addObject("id",board.getId());
            model.addObject("title",board.getTitle());
            model.addObject("contents",board.getContents());
            model.addObject("author",board.getAuthor());
            model.addObject("category",board.getCategory());
            model.addObject("member", member);
            return model;
    }

    @GetMapping("/admin/writerBoard")
    public ModelAndView writerBoard(@AuthenticationPrincipal Member member, ModelAndView model) {
        model.addObject("member",member);
        model.setViewName("writerBoard");
        return model;
    }



}
