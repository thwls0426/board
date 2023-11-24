package com.example.demo.service;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentDTO findById(Long id){
        Comment comment = commentRepository.findById(id).get();
        return CommentDTO.toCommentDTO(comment);
    }

    @Transactional
    public Comment save(CommentDTO commentDTO) {
        commentDTO.getBoardId();
        Optional<Board> optionalBoard = boardRepository.findById(commentDTO.getBoardId());
        commentDTO.setCreateTime(LocalDateTime.now());

        if(optionalBoard.isPresent()){
            Board board = optionalBoard.get();
            return commentRepository.save(commentDTO.toCommentEntity()); //모든 분기에서 return을 넣어야 오류가 안난다. 현재는 null 인데 추ㅜㅎ 수정할 에정
        } else{
            return null;
        }
    }
}
