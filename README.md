# 🚀 블로그 API 서버 튜토리얼
간단한 블로그 API 서버 개발 및 인프라 배포 연습을 위한 튜토리얼 저장소입니다. 

## 블로그 ERD

<details>
  <summary>ERD Sudo코드</summary>

    Table users {
    id integer [primary key]
    name varchar(255) [not null]
    email varchar(255) [unique, not null]
    password varchar(255) [not null]
    }
    
    Table posts {
    id integer [primary key]
    title varchar(255) [not null]
    description text [not null]
    user_id integer
    }
    
    
    Ref: users.id < posts.user_id 
</details>

- Sudo코드 ERD 추출을 위한 사이트: https://dbdiagram.io/d

### ERD
![스크린샷 2024-01-07 오후 12.51.54.png](..%2F..%2F%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202024-01-07%20%EC%98%A4%ED%9B%84%2012.51.54.png)


## 기능 요구사항

### 회원(user)
- [x] 회원가입을 한다.
  - [x] 닉네임(name)은 1 ~ 10자 사이의 한글 또는 영어만 입력 가능
  - [x] 이메일(email)은 이메일 형식이여야 하고 중복 불가능
  - [x] 비밀번호(password) 6 ~ 15자 사이의 영어,숫자,특수기호 조합만 입력 가능

- [x] 로그인을 한다.(JWT 토큰 발급)
  - [x] 존재하지 않는 회원은 로그인 불가


### 포스트(Post)
- [x] 포스트를 등록한다.
  - [x] 회원만 포스팅 가능
  - [x] 제목(title)은 1 ~ 10자 사이의 한글 또는 영어만 입력 가능
  - [x] 내용(description)은 최소 한글자 이상

- [x] 전체 포스트 목록을 조회한다.(페이징,필터 구현 안함)
  
- [x] 포스트 상세 조회한다. 
  - [x] 저장되지 않은 포스트를 조회하려하면 예외 발생
  - [x] 포스트에 등록된 코멘트 목록을 작성자 정보와 함께 조회

### 댓글(Comment)
- [x] 포스트에 해당하는 댓글을 등록한다.
  - [x] 회원만 댓글 가능 
  - [x] 등록하려는 포스트가 있어야 함
  - [x] 내용(description)은 최소 한글자 이상(공백 가능)

