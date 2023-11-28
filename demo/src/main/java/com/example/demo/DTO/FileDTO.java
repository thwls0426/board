package com.example.demo.DTO;

import com.example.demo.entity.BoardFile;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

// DTO 의 값이 entity의 값과 항상 같지 않다.
/*궁극적으로는 entity 를 사용할것이지만, 어떻게 사용할 것인가? 에 대한것.
* 이건 안전하게 저장되기 위한것. entity 를 즉시 사용할 수 없기 때문에 dto 사용. 하지만 dto는 entity가 될 순 없다.*/
@Data
@ToString
@NoArgsConstructor
public class FileDTO {

    private Long id;

    // ** 파일 경로 -- 이건 최종경로. 하지만 최종경로로 들고있지 않아야한다.
    private String filePath;

    // ** 파일 이름
    private String fileName;

    // ** UUID (랜덤 키) 무조건 저장이 되어야함.
    private String uuid;
    // uuid + 파일명 -> 이런구조이기 때문에, uuid도 필요하다.

    // ** 파일 포맷
    private String fileType;

    // ** 파일 크기
    private Long fileSize;

    // ** 게시물 id , 게시판의 키(PK) 이건 필수! 이걸로 보드를 검색해서 보드에 넣는과정임.
    private Long boardId;

    public BoardFile toEntity() {
        return BoardFile.builder()
                .filePath(filePath)
                .fileName(fileName)
                .fileSize(fileSize)
                .fileType(fileType)
                .uuid(uuid)
                .build();
    }
}

