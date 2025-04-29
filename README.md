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
|[TL]한승훈|[gitHub](https://github.com/sleepyhoon)|-**문서**: OpenAPI(Swagger) <br> -**기능**: 로그인, 회원가입 페이지 CSS, 관리자페이지, 이메일 스케쥴링 |
|배문성|[gitHub](https://github.com/heets-blue)|-**문서**: readme  <br>   -**기능**: CoffeeCRUD, CoffeeOrder저장, Coffee이미지 구현, Coffee 재고 기능 구현|                                
|탁서윤|[gitHub](https://github.com/peng255/)|-**기능**: 주문페이지, 유저페이지, 로그인(Spring Security), UserCR|
|최희웅|[gitHub](https://github.com/chw0912)|-**기능**: OrderCRUD, 유저페이지 주문목록 조회 및 디자인|
|이예원|[gitHub]()|-**기능**: 주문 직후/처리 완료 후/주문접수 실패시  email전송기능, 메일본문 페이지, 이메일 스케쥴링|


---
## ERD
![ERD](https://github.com/user-attachments/assets/aff2c7eb-7d15-432d-9344-5804372cd493)



## Flow Chart
![Flow-Chart](https://github.com/user-attachments/assets/5b105c2a-443a-4d89-84b6-aa55f6d247c9)

---

# 🚀Troubleshooting

### 프론트엔드와 백엔드를 하나의 레포지토리에서 관리할 때 Git 인식 문제

토이 프로젝트를 진행하면서, 프론트엔드(React)와 백엔드(Spring Boot)를 **하나의 깃허브 레포지토리**에서 관리하고자 했다.

그러나 프로젝트를 구성하는 과정에서 **Git 인식 문제**가 발생했다.

- root 디렉토리와 backend 디렉토리 모두 각각 `.git` 파일을 만들어버렸다.
- Git이 어느 디렉토리를 기준으로 동작해야 하는지 혼란이 있어 의도한 커밋이나 푸시가 제대로 되지 않거나, 경로 인식에 오류가 생겼다.
- 알고보니 인텔리제이에서 서브 디렉토리만 Project로 열더라도, 내부적으로 상위 디렉토리의 .git을 찾아서 연동한다고 한다. 그래서 commit, push가 모두 가능하다고 한다.

따라서 **하위 디렉토리에 있는 .git 파일을 삭제하면 해결되는 문제**였다.

---

### index에서 요청 받은 후 order객체 저장 중 오류

- **문제상황**

결제하기 버튼 클릭 후 콘솔에서 500 error와 아래의 오류 문구 생성

```html
A collection with orphan deletion was no longer referenced by the owning entity instance: io.sleepyhoon.project1.entity.Order.coffeeOrders
```

로그를 통해 form에서 요청 보내는 데이터가 문제가 있는지 확인해봤지만 입력값은 맞게 들어가 있었다.

- **해결내용**

OrderService의 save 메서드에서 Order 객체에 들어갈 CoffeeOrder 리스트를 만든 후 `order.setCoffeeOrders(coffeeOrders);`로 값을 설정 중이었다.

Order 엔티티의 coffeeOrders collection에 `orphanRemoval=true` 옵션이 걸려있는데, 컬렉션 자체를 통째로 새 리스트로제

그러니 컬렉션 객체 자체 (리스트 주소)는 그대로 두고 그 안의 내용만 add/remove/clear로 조정해주어야 한다.

`setCoffeeOrders()` 대신 아래의 코드로 설정해주니 오류가 해결되었다.

```java
// 잘못된 코드 (에러 발생)
order.setCoffeeOrders(newCoffeeOrderList);

// 해결된 코드
order.getCoffeeOrders().clear(); // 기존 값 비우고
order.getCoffeeOrders().addAll(newCoffeeOrderList); // 새 값 추가
```

---

### 클라이언트의 이미지 URL 요청을 서버가 찾지 못하는 문제

- **문제상황**
    
    클라이언트가 서버에 보내는 URL요청을 서버에서 찾지못해 404예외가 발생
    

- **해결내용**
    
    스프링 부트는 기본적으로 `classpath:/static/`, `classpath:/public/` **안에 있는 파일**만 자동으로 매핑하나 img폴더는 프로젝트 루트 디렉토리에 위치하여 찾지 못하여 404발생
    
    img폴더는 static하지 않다고 생각하여 이곳에 위치시키기 싫었다. 
    
    이를 `WebMvcConfig` 을 구현함으로 해결
    
    ```java
    @Configuration
    public class WebMvcConfig implements WebMvcConfigurer {
        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry
                    .addResourceHandler("img/**")          // 클라이언트 요청 URL
                    .addResourceLocations("file:./img/");   // 실제 서버 폴더 경로
        }
    }
    ```
    
    `addResourceHandlers` 은 `img/**`으로 들어오는 요청을 실제 서버의 파일 시스템 경로인  `file:./img/`으로 매핑해주어 해결할 수 있었다.
    
    `file:`은 **"**서버 파일 시스템에 존재하는 실제 파일 경로**"** 를 뜻
    

---

### 프론트에서 GET 요청 데이터를 불러오는 과정에서 JSON 파싱 오류 발생

- **문제 상황**
    - 프론트에서 주문 내역을 조회하는 GET 요청을 보냈지만, 응답 데이터(JSON)를 제대로 파싱하지 못하는 문제가 발생했다.
    - 브라우저 콘솔에서 undefined 출력과 함께 정상적인 데이터 매핑이 이루어지지 않았다.
- **문제 원인**
    - OrderResponseDto 내부에 List<CoffeeListDto> 형태의 필드를 가지고 있었는데,
    - 프론트에서는 이를 다음과 같이 잘못 파싱하고 있었다:
    
    ```java
    const counts = {};
        document.querySelectorAll('#summary-list .product-count').forEach(function(span) {
          const name = span.dataset.name;
          const count = parseInt(span.textContent);
          if (count > 0) { // 0개는 주문에 포함하지 않음
            counts[name] = count;
          }
        });
        
        ...
        
        const data = {
    	  "coffee-list": counts,
    	  "email": document.getElementById('email').value,
    	  "address": document.getElementById('address').value,
    	  "postnum": document.getElementById('postnum').value,
    	  "price": totalPrice,
    };
    ```
    
    ```json
    // 배열이 아닌 객체 형태
    {
      "coffee-list": { "coffeeName": "아메리카노", "quantity": 3 },  
      "price": 4500,
      "email": "user@example.com",
      "address": "서울시 강남구",
      "postNum": "12345"
    }
    ```
    
    - 위 코드에서 counts는 일반 **객체** 형태로 구성되었다.
    - 즉, coffee-list에 들어가는 값이 서버에서 기대하는 배열이 아니라, **객체** 형태로 만들어진 것이다.
    
    - 서버가 기대하는 응답 형식은 다음과 같이 배열 형태이다.
    
    ```json
    {
      "coffee-list": [
        { "coffeeName": "아메리카노", "quantity": 2 },
        { "coffeeName": "카페라떼", "quantity": 3 }
      ],
      "price": 9500,
      "email": "user@example.com",
      "address": "서울시 강남구",
      "postNum": "12345"
    }
    ```
    
- **문제 해결**
    - counts 객체를 순회하여, 배열 형태로 변환하는 로직을 추가하였다.
    
    ```jsx
    // counts 객체를 배열로 변환
    const coffeeList = [];
    for (const [coffeename, quantity] of Object.entries(counts)) {
      coffeeList.push({
        coffeeName: coffeeName,
        quantity: quantity
      });
    }
    
    const data = {
      "coffee-list": coffeeList,  
      "email": document.getElementById('email').value,
      "address": document.getElementById('address').value,
      "postnum": document.getElementById('postnum').value,
      "price": totalPrice,
    };
    ```
    
    - coffee-list가 올바르게 배열로 변환되면서 정상적으로 조회 기능이 동작했다.
- **배운점**
    - 객체와 배열의 JSON 형식이 어떻게 이루어지는지 명확히 알 수 있었고, 협업에서는 요청과 응답의 구조를 확실하게 정한 다음 코드를 구현해야한다는 것도 깨닫게 되는 경험이었다.
    - 또한 이번 프로젝트를 하면서 요청과 응답 구조를 어떻게 구성하면 되는지도 확실하게 알 수 있었다.

---

### 서버 예외처리에 대한 문제

- **문제상황**
    
    서버에서 예외가 발생했을 때, 서버 내부 구조가 노출된 에러 메시지가 그대로 출력되어 보안상 위험이 있었고, 클라이언트는 문제 원인을 알기 어려웠다. 특히 서버 예외로 인해 앱이 종료될 경우 사용자 경험에 큰 불편을 줄 수 있어 이에 대한 개선이 필요했다.
    
- **해결내용**
    
    @RestControllerAdvice을 사용

