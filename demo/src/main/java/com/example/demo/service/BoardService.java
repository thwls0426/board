package com.example.demo.service;
import com.example.demo.DTO.BoardDTO;
import com.example.demo.DTO.FileDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.File;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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

    private final String filepath = "C:/Users/thwls/OneDrive/바탕 화면/";
                                            //본인PC명
   //경로지정법 >> 프로젝트 우클릭 - 다음에서 열기 - 탐색기(Explorer) - 파일 열리면 주소창 클릭 - 복붙
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

    @Transactional
    public void save(BoardDTO dto, MultipartFile[] files) throws IOException{
        dto.setCreateTime(LocalDateTime.now());
        boardRepository.save(dto.toEntity());


        //** 파일 정보 저장 위치!
        // file 자체는 seter가 없으니 dto로 만들어서 보낸다.
        for(MultipartFile file : files){
            createFilePath(file);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setFileName(file.getOriginalFilename());

            fileRepository.save(fileDTO.toEntity());
        }
    }

    // ** service 가 싱글톤으로 만들어짐. .찍을때 밑의 애가 호출되지 않도록.
    // uuid 작업할건데 하나하나 세팅하기.
    private String createFilePath(MultipartFile file) throws IOException {

        Path uploadPath = Paths.get(filepath); // 저장을 어디해놓을지에 대한 경로

        // ** 만약 경로가 없다면, 경로 생성.
        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        // ** 파일명 추출
        String originalFilename = file.getOriginalFilename();
//        System.out.println(originalFilename); //실행해서 확장자 붙어잇는지 아닌지 여부확인

        // ** 확장자 추출
        String formatType = originalFilename.substring(
                originalFilename.lastIndexOf(".")); //. 뒤에거 갖고오셈

//        System.out.println(formatType);

        //파일이름에서 .뒤에거 빼기
        // ** 파일 이름만 남기기
        originalFilename=originalFilename.substring(
                0, originalFilename.lastIndexOf("."));
        System.out.println(originalFilename);

        // ** UUID 생성
        String uuid = UUID.randomUUID().toString();

        Path path = uploadPath.resolve(
         uuid + originalFilename + formatType
        );

        // ** 저장
        Files.copy(
                file.getInputStream(),
                path,
                StandardCopyOption.REPLACE_EXISTING);
        return "";
    }

    @Transactional
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(BoardDTO boardDTO) {
        Optional<Board> boardOptional = boardRepository.findById(boardDTO.getId());

        //if(boardOptional.isPresent()) ... 예외처리 생략
        Board board = boardOptional.get();

        board.updateFromDTO(boardDTO);

        boardRepository.save(board);
    }
}
