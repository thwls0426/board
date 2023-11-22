package com.example.demo.controller;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDTO boardDTO){
        boardDTO.setCreateTime(LocalDateTime.now()); //현재시간 출력
        log.info(boardDTO.toString());
//        Board board = boardDTO.toEntity();
        boardRepository.save(boardDTO.toEntity());
    }
}
