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
  - 인가 기능(Admin, User)

### 이메일 전송 기능
  - 주문성공 시 메일 전송 기능
  - 매일 오후2시에 배송안내 이메일 일괄 전송기능

---


  # 역할 분담
  
|이 름|GitHub|역할|
|:---:|---|---|
|[TL]한승훈|[gitHub](https://github.com/sleepyhoon)|-**문서**: OpenAPI(Swagger) <br> -**기능**: 관리자페이지, 이메일 스케쥴링 |
|배문성|[gitHub](https://github.com/heets-blue)|-**문서**: readme  <br>   -**기능**: CoffeeCRUD, CoffeeOrder저장, Coffee이미지 구현, Coffee 재고 기능 구현|                                
|탁서윤|[gitHub](https://github.com/peng255/)|-**기능**: 주문페이지, 유저페이지, 로그인(Spring Security), UserCR|
|최희웅|[gitHub](https://github.com/chw0912)|-**기능**: OrderCRUD, 유저페이지 주문목록 조회 및 디자인|
|이예원|[gitHub]()|-**기능**: 주문 직후/처리 완료 후 email전송기능|
