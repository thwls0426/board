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
public class BoardDTO { //contents , titel() 값만 html에서 필요하니까 이걸 갖고옴. dto 에선 column 안써도 된당

    private Long id;
    //정렬하기위해 구분
    // ** 게시물 제목
    private String title;

    // ** 작성한 게시글 내용
    private String contents;

    private LocalDateTime createTime;

    // ** 최근 수정시간
    private LocalDateTime updateTime;

    public Board toEntity() {
        return Board.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .createTime(createTime) // 최초들어간 값만 저장
                .updateTime(LocalDateTime.now()) // 수정만들면 수정데이터 들어감.
                .build();
    }
    //entity -> dto 변경 함수
    public static BoardDTO toBoardDTO(Board board){
        return new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                board.getCreateTime(),
                board.getUpdateTime());
    }
}
