package com.example.demo.DTO;


import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private Long id;

    private Long boardId;

    private String writer;

    private String contents;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


    public Comment toCommentEntity() {
        Comment comment = Comment.builder()
                .id(id)
                .writer(writer)
                .contents(contents)
                .createTime(createTime)
                .updateTime(LocalDateTime.now())
                .board((boardId != null) ? Board.builder().Id(boardId).build() : null)
                .build();

        return comment;
    }

    public static CommentDTO toCommentDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                (comment.getBoard() != null) ? comment.getBoard().getId() : null,
                comment.getWriter(),
                comment.getContents(),
                comment.getCreateTime(),
                comment.getUpdateTime());
    }
}
