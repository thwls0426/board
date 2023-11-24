package com.example.demo.DTO;


import com.example.demo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    private Long boardId;

    private String writer;

    private String contents;


//    private Comment toEntity(){
//        return Comment.bulider;
//    }
}
