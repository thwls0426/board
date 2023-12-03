package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class FileDownloadController { //파일 다운로드를 가능하게 해주는 컨트롤러
    @GetMapping("/download/{uuid}/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String uuid,
                                                 @PathVariable String fileName){
        Path filePath = Paths.get("C:/Users/thwls/OneDrive/바탕 화면/" + uuid + fileName);

        try{
            Resource resource = new UrlResource(filePath.toUri());

            if(resource.exists() || resource.isReadable())
                return ResponseEntity.ok()
                        .header("Content-Disposition",
                                "attachment; filename = \"" + resource.getFilename()+"\"")
                        .body(resource);

            else { //없으면 찾을 수 없음
                return  ResponseEntity.notFound().build();
            }
        }catch (Exception e){ //아예 없는 경우
            return ResponseEntity.internalServerError().build();

        }
    }
}
