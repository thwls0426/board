package com.example.demo.service;
import com.example.demo.DTO.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.BoardFile;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BoardService {

    /*게시물 여부를 여기서 체크해서 불러옴
      파일여부는 board 에 잇어야한다. 보드에다가 파일 여부를 체크할 수 있는 장치 세팅

      board 저장소(db)에서 게시물을 다 불러옴 . 이때 파일이 있네(true 값.)? 파일 저장소로 가서 파일을 불러옴
      이 구조를 만드려면, 보드 서비스에서 파일 레퍼지토리에 접근할 수 있어야 한다.
      파일 유무를 체크하고 파일이 있으면 불러오는건 service 에서 받고, repository로 접근해야함.
      filecontroller 와 fileservice 는 굳이 작성될 필요가 없다. 하지만 entity 와 repository는 있어야함!(요구사항)
    * */
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    private final String filePath = "C:/Users/thwls/OneDrive/바탕 화면/";
                                            //본인PC명
   //경로지정법 >> 프로젝트 우클릭 - 다음에서 열기 - 탐색기(Explorer) - 파일 열리면 주소창 클릭 - 복붙
    // 이 경로는 전달되어야 함.
    // ** paging 을 함수
    public Page<BoardDTO> paging(Pageable pageable) {

        // ** 페이지 시작 번호 셋팅
        int page = pageable.getPageNumber() - 1;

        // ** 페이지에 포함될 게시물 개수
        int size = 5;

        // ** 전체 게시물을 불러온다.
        Page<Board> boards = boardRepository.findAll(
                PageRequest.of(page, size));

        return boards.map(board -> new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                board.getCreateTime(),
                board.getUpdateTime() ));
    }

    public BoardDTO findById(Long id) {

        //if(boardRepository.findById(id).isPresent()) ... 예외처리 생략
        Board board = boardRepository.findById(id).get();
        return BoardDTO.toBoardDTO(board);
    }

    //** 파일 정보 저장 위치!
    @Transactional
    public void save(BoardDTO dto, MultipartFile[] files) throws IOException {


        Path uploadPath = Paths.get(filePath);

        // ** 만약 경로가 없다면 경로 생성.
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        dto.setCreateTime(LocalDateTime.now());
        // ** 게시글 DB에 저장 후 pk을 받아옴.
        Long id = boardRepository.save(dto.toEntity()).getId();
        Board board = boardRepository.findById(id).get();


        // ** 파일 정보 저장.
        for (MultipartFile file : files) {

            // ** 파일명 추출
            String originalFileName = file.getOriginalFilename();

            if (originalFileName != null && !originalFileName.isEmpty() && originalFileName.contains(".")) {

                // ** 확장자 추출
                String formatType = originalFileName.substring(
                        originalFileName.lastIndexOf("."));

                // ** UUID 생성
                String uuid = UUID.randomUUID().toString();

                // ** 경로 지정
                // ** C:/Users/G/Desktop/green/Board Files/{uuid + originalFileName}
                String path = filePath + uuid + originalFileName;

                // ** 경로에 파일을 저장.  DB 아님
                file.transferTo(new File(path));

                BoardFile boardFile = BoardFile.builder()
                        .filePath(filePath)
                        .fileName(originalFileName)
                        .uuid(uuid)
                        .fileType(formatType)
                        .fileSize(file.getSize())
                        .board(board)
                        .build();

                fileRepository.save(boardFile);
            }
        }
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(BoardDTO boardDTO) {
        Optional<Board> boardOptional = boardRepository.findById(boardDTO.getId());
        boardDTO.setUpdateTime(LocalDateTime.now());

        //if(boardOptional.isPresent()) ... 예외처리 생략
        Board board = boardOptional.get();

        board.updateFromDTO(boardDTO);

        boardRepository.save(board);
    }
}
