package com.example.demo.controller;

import com.example.demo.DTO.BoardDTO;
import com.example.demo.service.BoardService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board") //컨트롤러도 이런걸 써서 구분화 시키기
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/create") // / << 이건 홈 . 홈에가면 작성해논 게시물을 쫙 보여주도록 할 예정
    public String create(){
        return "create"; //게시물작성
    }

    @GetMapping(value = {"/paging", "/"}) //select. R . 값 2개면 value 라고 써야 한다!
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

    @GetMapping("/update/{Id}")
    public String updateForm(@PathVariable Long Id, Model model){ //<변경전데이터>
        // 이동시키는 것. get은 업데이트로 못쓰기때문에. 이걸 update.html로 넘겨서 갖고옴.

        BoardDTO dto = boardService.findById(Id);
        model.addAttribute("board", dto);
        return "update"; //변경할 데이터를 지정하는 역할 이걸 update.html 로 보냄
    }

    @PostMapping("/update") //update.html 데이터를 갖고와서 변경되었다고 dto로 저장. <변경 후 데이터>
    public String update(@ModelAttribute BoardDTO boardDTO){
        // 여기서 업데이트가 이루어짐.

        boardService.update(boardDTO);
        return "redirect:/board/";
    }



    @GetMapping("/{Id}") //select. R
    public String paging(@PathVariable Long Id, Model model, @PageableDefault(page = 1) Pageable pageable) {

        BoardDTO dto/*얜 엔티티*/ = boardService.findById(Id);

        model.addAttribute("board", dto);
        //얘는 detail 의 <th>id 여기 밑의 저거랑 같아야한다
        model.addAttribute("page", pageable.getPageNumber());

        return "detail";
    }

    @PostMapping("/save") // Update
    public String save(@ModelAttribute BoardDTO boardDTO){
        System.out.println(boardDTO.getTitle() + " : " + boardDTO.getContents());
        boardService.save(boardDTO);
        return "redirect:/board/";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        System.out.println(id);
        boardService.delete(id);
        return "redirect:/board/paging";
    }

}
