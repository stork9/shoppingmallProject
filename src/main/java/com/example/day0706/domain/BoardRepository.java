package com.example.day0706.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByModifiedAtBetweenOrderByModifiedAtDesc(LocalDateTime start, LocalDateTime end);

}
