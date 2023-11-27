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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        System.out.println(id);
        boardService.delete(id);
        return "redirect:/board/paging";
    }

    @PostMapping("/save") // Update.
    public String save(@ModelAttribute BoardDTO boardDTO,
                       @RequestParam MultipartFile[] files) throws IOException {

                                                            // 이거쓰먄 html도 파일불러오게 수정해야함.
        System.out.println(boardDTO.getTitle() + " : " + boardDTO.getContents());
        boardService.save(boardDTO, files);


        return "redirect:/board/";
    }
    /* ** 파일 업로드
     파일 업로드 만들기도 여기서 진행. 컨트롤러는 필요 없음 왜? 보통 게시물 쓸때 발생하니까.
      그럼 이 파일의 원형은 어디에 보관되는가? -- 어딘가의 공간. 서버. --> 실행된 PC가 서버 자체가 된다.
      DB 와 파일은 어디에 저장되는가? -- 개인별로 지정할 수 있다. 개인 네트워크(내부 연결망으로 연결된.), 주소 공개된 PC(접속 허용된)
      서버 내에 DB 저장공간, 파일 저장공간이 있다.
      이때, 스프링에서 db에 저장하고, 파일은 파일 서버에 저장할것이다. 경로는 내PC의 경로로 만들어서 씀.
      파일의 정보(경로. 파일이 어디에 저장될 것인지에 대한 것) -- db서버, 원본 -- 파일서버
      파일을 업로드 하기 위해 필요한 것 : 파일이 저장될 공간의 "주소", 파일의 "이름" 필요, 파일 "사이즈(크기)", 파일 "타입" .. (개인이 알아서)
      파일 업로드 시 , 서버에서 db를 참조하여 파일 서버에 접속하여 연결.
      파일경로 설정시, User명을 써주는게 좋다 (이건 PK 일거기 때문)
      이름 같은걸 방지하기 위해 'UUID(Universally Unique iDentifier : 무작위 식별자. 동일한 코드 안나옴)' 라는것을 사용한다.
      -->> 이걸 key로 쓸 수는 있는데, 복잡해서 쓰진않음.
      경로 + UUID + 파일이름.타입 --> 이렇게 불러옴. 이건 회사마다 기준이 다를수 있음.
    * */

}
