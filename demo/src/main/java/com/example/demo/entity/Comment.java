package com.example.demo.entity;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    private Long id;
    // Id 하니까 오류떠서 id로 바꿈!

    @Column(length = 50)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id") // 자동으로 id 가설정되지만, 문제가 될 수도 있으니 따로 설정함.
    private Board board;
}
