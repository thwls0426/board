package com.example.demo.service;
import com.example.demo.DTO.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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
    public void save(BoardDTO dto) {
        dto.setCreateTime(LocalDateTime.now());
        boardRepository.save(dto.toEntity());
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
