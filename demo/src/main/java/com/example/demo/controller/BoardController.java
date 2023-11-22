package com.example.demo.controller;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.repository.BoardRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
//@RequestMapping("/board") 컨트롤러도 이런걸 써서 구분화 시키기
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/") // / << 이건 홈 . 홈에가면 작성해논 게시물을 쫙 보여주도록 할 예정
    public String home(){
        return "createBoard"; //게시물작성
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        System.out.println(boardDTO.getTitle() + " : " + boardDTO.getContents());
        boardService.save(boardDTO);
        return "redirect: ";
    }
}
