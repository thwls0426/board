package com.example.demo.service;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.DTO.CommentDTO;
import com.example.demo.entity.Board;
import com.example.demo.entity.Comment;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
        commentDTO.setCreateTime(LocalDateTime.now());
        Optional<Board> optionalBoard = boardRepository.findById(commentDTO.getBoardId());

        if(optionalBoard.isPresent()){
            Board board = optionalBoard.get();
            Comment entity = commentDTO.toCommentEntity();
            entity.toUpdate(board);
            return commentRepository.save(entity); //모든 분기에서 return을 넣어야 오류가 안난다. 현재는 null 인데 추ㅜㅎ 수정할 에정
        } else{
            return null;
        }
    }

    public List<CommentDTO> findAll(Long boardId) {
        Board boardEntity = boardRepository.findById(boardId).get();
        java.util.List<Comment> commentEntityList = commentRepository.findAllByBoardOrderByIdDesc(boardEntity);
        /* EntityList -> DTOList */
        List<com.example.demo.DTO.CommentDTO> commentDTOList = new ArrayList<>();
        for (Comment commentEntity: commentEntityList) {
            com.example.demo.DTO.CommentDTO commentDTO = com.example.demo.DTO.CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }


}

