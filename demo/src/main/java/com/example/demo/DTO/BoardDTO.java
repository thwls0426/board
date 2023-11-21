package com.example.demo.DTO;

import lombok.*;

import javax.persistence.Column;

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


}
