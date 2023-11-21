package com.example.demo.controller;

import com.example.demo.entity.Board;

import javax.persistence.Column;
import java.time.LocalDateTime;

public class BoardRequest {

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
