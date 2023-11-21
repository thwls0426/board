package com.example.demo.DTO;

import com.example.demo.entity.Board;
import lombok.*;

import javax.persistence.Column;
import java.util.Collections;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO { //contents , titel() 값만 html에서 필요하니까 이걸 갖고옴.

    // ** 게시물 제목
    @Column(length = 50)
    private String title;

    // ** 작성한 게시글 내용
    @Column(length = 256)
    private String contents;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .contents(contents)
                .build();
    }
}
