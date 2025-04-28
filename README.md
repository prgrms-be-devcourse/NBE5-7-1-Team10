# [NBES5-7-1-Team10] 1차 프로젝트
프로그래머스 백엔드 5기 7회차 10팀 1차 프로젝트

# ☕ 싱글벙글카페
## 프로젝트 개요
커피 원두 패키지를 판매하는 온라인 웹사이트를 구현
고객이 주문을 하면 해당 주문들을 바로 처리하지 않고
매일 전날 오후2시부터 오늘 오후2시까지의 주문을 모아서 처리하므로써 보다 효율적인 배송 시스템이 가능하다.

## 💻기술 스택

<div align=center>
    <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white">
    <img src="https://img.shields.io/badge/spring_boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    <img src="https://img.shields.io/badge/spring_security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white">
    <img src="https://img.shields.io/badge/java-F2302F?style=for-the-badge&logo=openjdk&logoColor=white">
    <img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
    <img src="https://img.shields.io/badge/jpa-F2302F?style=for-the-badge&logo=data&logoColor=white">
    <img src="https://img.shields.io/badge/lombok-EA7600?style=for-the-badge&logo=lombok&logoColor=white">
<img src = "https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E">

  
| 기술 | 버전 |
|-------|-------|
|  Java| OpenJDK 23.0.2 | 
| Spring Boot | 3.4.4 | 
|Spring Boot Libraries |Data JPA, Web, Security|
|Lombok|1.18.36|
|MySQL	MySQL Community|8.4.4|
|MySQL Connector| 9.1.0|
|HTML5|  - |
|javascript| - |



    
    
</div>


---

# 최소 요구사항(MVP)

### 고객관리
- 주문시 입력하는 email로써 고객을 구분
  - 하나의 email로 하루에 여러번 주문을 받아도 **하나로 합쳐서** 다음날 배송
- 고객에게 "당일 오후2시 이후의 주문은 다음날 배송을 시작합니다."라고 고지해야 한다.

### 제품 목록 조회
- 판매중인 제품들의 목록과 각 제품의 상세정보(이름, 가격)를 제공한다.

### 주문 관리
- 고객이 선택한 제품이 주문Summary(장바구니)에 추가되어야 한다.
- 한 주문에 고객의 이메일, 주소, 우편번호를 입력한다.

### 관리자 기능
- 관리자는 관리자페이지에 접근/관리 수행한다.
- 판매할 제품들을 추가, 수정, 삭제할 수 있다.


  ---
  
# 추가 기능
MVP외의 추가 구현한 기능
### 사용자 인증/인가 및 관리
  - 회원가입, 로그인
  - 인가 기능(ADMIN, MEMBER)

### 유저 마이페이지
  - 유저의 주문 내역을 확인할 수 있는 페이지
  - 현재 로그인된 정보를 불러와 자동으로 주문 내역을 보여줌
  - 배송 준비중 / 배송 완료 문구로 주문 처리 여부를 표시함
### 관리자페이지
  - 관리자 권한을 가진 유저만 접근할 수 있음.
  - 커피 종류 추가, 수정, 삭제 + 커피 재고 기능 추가
  - 커피 이미지를 첨부파일로 여러장 등록 가능
### 주문페이지
  - 현재 로그인한 사용자의 정보 화면에 표시
  - 권한에 따라 자동으로 관리자페이지 접근 버튼 보임 / 숨김
  - 재고 관리 기능
      - 5개 미만의 재고일 경우 “품절임박!” 효과 이미지에 표시
      - 품절이면 “품절” 효과 이미지에 표시
          - 품절 시 +-버튼 사라져 선택 불가하게 만듬 ⇒ 이위치에 Sold Out 표시
      - 재고보다 많은 양을 주문할 경우 주문을 진행하지 않고 사용자에게 재고를 알림
  - 결제하기 버튼을 누르면 주문내역 정보를 브라우저 팝업으로 알림
### 주문내역 메일 전송
  - 주문 완료 직후 유저에게 주문 확인 메일 전송
      - 본문에 주문번호와 항목, 품목, 수량, 총액 포함
  - 매일 14시에  24시간 내 주문한 유저 별로 전체 주문 내역 전송
      - 본문에 건당 상품 구성과 총액을 포함한 주문 내역 리스트 포함
---

  # 역할 분담
  
|이 름|GitHub|역할|
|:---:|---|---|
|[TL]한승훈|[gitHub](https://github.com/sleepyhoon)|-**문서**: OpenAPI(Swagger) <br> -**기능**: 관리자페이지, 이메일 스케쥴링 |
|배문성|[gitHub](https://github.com/heets-blue)|-**문서**: readme  <br>   -**기능**: CoffeeCRUD, CoffeeOrder저장, Coffee이미지 구현, Coffee 재고 기능 구현|                                
|탁서윤|[gitHub](https://github.com/peng255/)|-**기능**: 주문페이지, 유저페이지, 로그인(Spring Security), UserCR|
|최희웅|[gitHub](https://github.com/chw0912)|-**기능**: OrderCRUD, 유저페이지 주문목록 조회 및 디자인|
|이예원|[gitHub]()|-**기능**: 주문 직후/처리 완료 후 email전송기능|
