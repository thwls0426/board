# 🧑‍🏫 게시판

홈페이지의 초기 게시판 세팅을 위한 작업물 입니다.

***

###### (aplication.yml)
IDE: IntelliJ IDEA Community   
Gradle - Groovy, Java 17   
Jar 11   
Spring Boot 2.7.6   
jvm.convert 3.3.2   
JDK 11   
mysql 8.0.35   
Lombok   
Spring Web   
Spring Data JPA   
Thymeleaf   

---

## ※ 게시판 주요기능
1. 글쓰기(/board/save)
2. 글목록(/board/)
3. 글조회(/board/{id})
4. 글수정(/board/update/{id})
 - 상세화면에서 수정 버튼 클릭
 - 서버에서 해당 게시글의 정보를 가지고 수정 화면 출력
 - 제목, 내용 수정 입력 받아서 서버로 요청

---

## ※ 수정 처리
1. 글삭제(/board/delete/{id})
2. 페이징처리(/board/paging)
 - /board/paging?page=1
 - /board/paging/1
3. 게시글
4. 파일(이미지)첨부하기
5. 단일 파일 첨부
