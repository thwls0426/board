package com.example.demo.controller;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
//@RequestMapping("/board") 컨트롤러도 이런걸 써서 구분화 시키기
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/") // / << 이건 홈 . 홈에가면 작성해논 게시물을 쫙 보여주도록 할 예정
    public String home(){
        return "index"; //게시물작성
    }

    @GetMapping("/createBoard") // / << 이건 홈 . 홈에가면 작성해논 게시물을 쫙 보여주도록 할 예정
    public String createBoard(){
        return "createBoard"; //게시물작성
    }


    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        System.out.println(boardDTO.getTitle() + " : " + boardDTO.getContents());
        boardService.save(boardDTO);
        return "redirect: ";
    }


    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
                                                                            //얘가 MVC의 M
        Page<BoardDTO> boards = boardService.paging(pageable);

        // **
        int blockLimit = 3; // 페이지번호
        int startpage = (int) (Math.ceil((double) pageable.getPageNumber()/ blockLimit) -1) * blockLimit + 1;
        //123 - 456 으로 시작하ㅕ고..
        int endpage = ((startpage + blockLimit - 1) < boards.getTotalPages()) ? (startpage + blockLimit - 1) : boards.getTotalPages();

        //이름 잘 적기
        model.addAttribute("boardList", boards);
        model.addAttribute("startpage", startpage);
        model.addAttribute("endpage", endpage);

        return "paging";
    }
}
