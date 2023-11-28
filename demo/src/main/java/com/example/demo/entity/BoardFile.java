package com.example.demo.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class BoardFile {
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

    //연관관계 매핑. 이미 보드를 들고 잇기 떄문에 id를 굳이 갖고잇을 필요는 없다. entity와 dto 에서 들고잇는 값이 항상 같지 않다.
    //보드 id 는 갖고잇어야 한다 -- 어디에 속해야하는지 찾긴해야함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    private String uuid;

    @Builder
    public BoardFile(Long id, String filePath, String fileName, String fileType, Long fileSize, Board board, String uuid) {
        this.id = id;
        this.filePath = filePath;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.board = board;
        this.uuid = uuid;
    }
}
