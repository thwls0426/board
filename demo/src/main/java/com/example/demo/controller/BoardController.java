package com.example.demo.controller;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.entity.Board;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
//@RequestMapping("/board")
public class BoardController {

    private final BoardRepository boardRepository;

    public BoardController(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @GetMapping("/") // / << 이건 홈 . 홈에가면 작성해논 게시물을 쫙 보여주도록 할 예정
    public String home(){
        return "createBoard"; //게시물작성
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        System.out.println(boardDTO.getTitle() + " : " + boardDTO.getContents());
        log.info(boardDTO.toString());
        Board board = boardDTO.toEntity();
        boardRepository.save(board);
        return "";
    }
}
