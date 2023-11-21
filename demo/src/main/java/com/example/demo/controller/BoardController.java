package com.example.demo.controller;

import com.example.demo.DTO.BoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/board")
public class BoardController {

    @GetMapping("/") // / << 이건 홈 . 홈에가면 작성해논 게시물을 쫙 보여주도록 할 예정
    public String home(){
        return "createBoard"; //게시물작성
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        System.out.println(boardDTO.getTitle() + " : " + boardDTO.getContents());
        return "";
    }
}
