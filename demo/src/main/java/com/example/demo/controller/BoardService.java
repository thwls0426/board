package com.example.demo.controller;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

//@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDTO boardDTO){
        boardDTO.setCreateTime(LocalDateTime.now()); //현재시간 출력
        log.info(boardDTO.toString());
        boardRepository.save(boardDTO.toEntity());
    }

    public Page<BoardDTO> paging(Pageable pageable){ //페이징 진행, 이건 컨트롤러에서 해야함
            //페이지에 표현할 리스트    이건 페이지 정보, 갯수 등을 포함한 인터페이스
        // ** 페이지 시작번호 셋팅
        //페이지 수량. 1번부터 시작하기 위함. 이건 정형화 되어있는 코드. 0부터 시작하려면 -1 지우먀ㅕㄴ된다.
        int page = pageable.getPageNumber() - 1 ;
        //페이지에 몇개의 게시물을 넣을것인가?
        // ** 페이지에 포함될 게시물 개수
        int size = 5;

        // ** 전체 게시물을 불러옴
        Page<Board> boards = boardRepository.findAll(PageRequest.of(page, size));
                                                    //페이지 정렬
        //board-> 이부분부터는 람다식.
        //boards = 위의 Page<board>의 board .
        /*
         저 안에는 for문이 들어가 있음.. 무슨말이지?..... board가 for문의 List[i] 의 i와 같다.

        * */
        return boards.map(board -> new BoardDTO(
                board.getId(),
                board.getTitle(),
                board.getContents(),
                board.getCreateTime(),
                board.getUpdateTime()));
    }
}
