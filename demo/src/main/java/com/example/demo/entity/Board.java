package com.example.demo.entity;

import com.example.demo.DTO.BoardDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class Board {

    // ** PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    // ** 작성자 이름
    @Column(length = 50)
    private String userName;

    // ** 게시물 제목
    @Column(length = 50)
    private String title;

    // ** 작성한 게시글 내용
    @Column(length = 256)
    private String contents;

    // ** 최초 작성시간
    private LocalDateTime createTime;

    // ** 최근 수정시간
    private LocalDateTime updateTime;

    @Builder
    public Board(Long Id, String userName, String title, String contents, LocalDateTime createTime, LocalDateTime updateTime){
        this.Id = Id;
        this.userName = userName;
        this.title = title;
        this.contents = contents;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }


    public void updateFromDTO(BoardDTO boardDTO){
        // ** 모든 변경사항을 세팅 . 원래는 다 넣어야함! 나중에 다넣기

        this.title = boardDTO.getTitle();
        this.contents = boardDTO.getContents();
    }
}
