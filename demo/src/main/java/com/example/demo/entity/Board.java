package com.example.demo.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Board {

    // ** PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;

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
}
