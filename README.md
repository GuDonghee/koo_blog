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
  - [x] 이메일(email)은 이메일 형식이여야 하고 중복 불가능하다
  - [x] 비밀번호(password) 6 ~ 15자 사이의 영어,숫자,특수기호 조합만 입력 가능

### 포스트(Post)
- [x] 포스트를 등록한다.
  - [ ] 회원만 포스팅 가능
  - [ ] 제목(title)은 1 ~ 10자 사이의 한글 또는 영어만 입력 가능
  - [ ] 내용(description)은 최소 한글자 이상