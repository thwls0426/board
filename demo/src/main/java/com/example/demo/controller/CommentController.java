package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.entity.Comment;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Access;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    //에러 반환
    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO){

        System.out.println(commentDTO);
        Comment comment = commentService.save(commentDTO);

        List<CommentDTO> all = commentService.findAll(commentDTO.getBoardId());

        if (comment != null){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("게시글이 없습니다.", HttpStatus.NOT_FOUND);
        }

    }

}
