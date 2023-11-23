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
