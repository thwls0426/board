package com.example.demo.entity;

import com.example.demo.DTO.BoardDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
//@Table --> 이름 및 설정도 바꿀 수 있다. 주로 이름 쓰려고 바꿈

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

    //연관관계 매핑은 테이블에 올라간것이기 때문에 entity 에 작성해야함 . entity = table .
    // ** 소유와 비소유의 관계. 비소유(many) 쪽에서 항상 pk를 확인함.
    // remove - 소유한 쪽에서 데이터를 지웠을 때, 비소유쪽을 지우는 것. 예를들어 게시물을 지우면 댓글을 날리는 것.
    // ** cascade = CascadeType.Remove : 게시물이 삭제되면 댓긍르 자동으로 삭제.
    // ** orphanRemoval = true : 연결관계가 끊어진다면, 자동으로 삭제.
    // ** fetch = FetchType.LAZY : 지연로딩. 성능 최적화
    /*Bean : 스프링의 객체.
      entity 의 트랜젝션 발생 > bean이 날리고 사라짐. 그니까 이게 순식간이라 밑의 내용을 다 불러오지 못할 수 있으니 지연로딩을 같이 써야함. */
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY) //Comment 에 있는 변수명을 적어준다. 연관관계 매핑.
    private List<Comment> comment = new ArrayList<>();


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
