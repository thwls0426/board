package com.example.demo.entity;


import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ** 파일 경로
//    @Column(nullable = false)
    private String filePath;

    // ** 파일 이름
//    @Column(nullable = false)
    private String fileName;

    // ** 파일 포맷
    // 얘는 길이가 많이 길 수 있으니까 길이를 설정하지 않는게 맞다.
    @Column
    private String fileType;

    // ** 파일크기
    @Column
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    public File(Long id, String filePath, String fileName, String fileType, Long fileSize, Board board) {
        this.id = id;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.board = board;
    }
}
