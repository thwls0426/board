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
        return Comment.builder()
                .id(id)
                .writer(writer)
                .contents(contents)
                .createTime(createTime)
                .build();

    }

    public static CommentDTO toCommentDTO(Comment comment, Long boardId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(comment.getId());
        commentDTO.setWriter(comment.getWriter());
        commentDTO.setContents(comment.getContents());
        commentDTO.setCreateTime(comment.getCreateTime());
        commentDTO.setBoardId(boardId);
        return commentDTO;
    }
}
