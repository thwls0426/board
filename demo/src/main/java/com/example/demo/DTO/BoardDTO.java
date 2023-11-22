package com.example.demo.DTO;

import com.example.demo.entity.Board;
import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Collections;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO { //contents , titel() 값만 html에서 필요하니까 이걸 갖고옴.

//    private Long Id;
    //정렬하기위해 구분
    // ** 게시물 제목
    @Column(length = 50)
    private String title;

    // ** 작성한 게시글 내용
    @Column(length = 256)
    private String contents;

    private LocalDateTime createTime;

    // ** 최근 수정시간
    private LocalDateTime updateTime;

    public Board toEntity() {
        return Board.builder()
//                .Id(Id)
                .title(title)
                .contents(contents)
                .createTime(createTime) // 최초들어간 값만 저장
                .updateTime(LocalDateTime.now()) // 수정만들면 수정데이터 들어감.
                .build();
    }
}
