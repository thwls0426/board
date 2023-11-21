package com.example.demo.controller;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDTO boardDTO){
        log.info(boardDTO.toString());
        Board board = boardDTO.toEntity();
        boardRepository.save(boardDTO.toEntity());
    }
}
