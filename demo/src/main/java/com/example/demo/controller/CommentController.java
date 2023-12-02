package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.entity.Comment;
import com.example.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Access;
import java.util.List;
import java.util.Optional;

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
    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/list/{boardId}")
    public ResponseEntity<List<CommentDTO>> getComments(@PathVariable Long boardId) {
        List<CommentDTO> comments = commentService.findAll(boardId);
        return new ResponseEntity<>(comments, HttpStatus.OK);

    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){ //<변경전데이터>
        // 이동시키는 것. get은 업데이트로 못쓰기때문에. 이걸 update.html로 넘겨서 갖고옴.

        Optional<Comment> dto = commentService.findById(id);
        model.addAttribute("commentDTO", dto);
        return "update"; //변경할 데이터를 지정하는 역할 이걸 update.html 로 보냄
    }


    @PostMapping("/update") //update.html 데이터를 갖고와서 변경되었다고 dto로 저장. <변경 후 데이터>
    public String update(@ModelAttribute CommentDTO commentDTO){
        // 여기서 업데이트가 이루어짐.

        commentService.update(commentDTO);
        return "redirect:/board/paging/";
    }

}
