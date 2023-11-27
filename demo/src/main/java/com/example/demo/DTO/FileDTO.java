package com.example.demo.DTO;

import com.example.demo.entity.File;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;

@Data
@ToString
@NoArgsConstructor
public class FileDTO {

    private Long id;

    // ** 파일 경로
    private String filePath;

    // ** 파일 이름
    private String fileName;

    // ** 파일 포맷
    private String fileType;

    // ** 파일크기
    private Long fileSize;

    // ** 게시물 id
    private Long boardId;

    public File toEntity() {
        return File.builder()
                .filePath(filePath)
                .fileName(fileName)
                .fileSize(fileSize)
                .fileType(fileType)
                .build();
    }
}

