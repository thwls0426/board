package com.example.demo.entity;

import com.example.demo.DTO.CommentDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Id 하니까 오류떠서 id로 바꿈!

    @Column(length = 50)
    private String contents;

    private String writer;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // 자동으로 id 가설정되지만, 문제가 될 수도 있으니 따로 설정함.
    private Board board;

    @Builder
    public Comment(Long id, String writer, String contents, Board board, LocalDateTime createTime, LocalDateTime updateTime){
        this.id = id;
        this.writer = writer;
        this.contents = contents;
        this.board = board;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Comment toUpdate(Board board) {
        Comment comment = new Comment();
        this.board = board;
        return comment;
    }
    public void updateFromCommentDTO(CommentDTO commentDTO){
        this.writer = commentDTO.getWriter();
        this.contents = commentDTO.getContents();
        this.updateTime = LocalDateTime.now();
    }
}