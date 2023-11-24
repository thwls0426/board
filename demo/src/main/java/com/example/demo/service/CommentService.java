package com.example.demo.service;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Comment save(CommentDTO commentDTO) {
        commentDTO.getBoardId();
        Optional<Board> optionalBoard = boardRepository.findById(commentDTO.getBoardId());

        if(optionalBoard.isPresent()){
            Board board = optionalBoard.get();



//         확인용   System.out.println(board.getTitle());
//            System.out.println(board.getContents());
            return null; //모든 분기에서 return을 넣어야 오류가 안난다. 현재는 null 인데 추ㅜㅎ 수정할 에정
        } else{
            return null;
        }
    }
}
