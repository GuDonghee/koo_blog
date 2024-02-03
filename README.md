# 🚀 블로그 API 서버 
간단한 블로그 API 서버 개발 및 인프라 배포 연습을 위한 튜토리얼 저장소입니다. 

</br>
</br>


## 🏃블로그 인프라 시나리오
- 블로그 홍보 성공으로 특정 시간대(오후 8~12)시 사이에 트래픽이 급증하는 현상을 보임
- 유저 CS사항으로 포스팅 이미지 첨부에 대한 비지니스 요구사항이 발생함

### 대응 방법
1. EC2 AutoScaling 그룹을 통해 두대의 서버 자원으로 트래픽을 분산 시킴 (Private subnet 이전) 
2. ALB를 통해 두대의 EC2 서버에 트래픽 분산하도록 구성 (Public subnet에 생성)
3. 블로그 포스팅 시 , S3릃 통해 서버 사이드로 이미지 파일을 업로드하도록 구성 

완료된 인프라 이미지는 하단 `인프라 구조`에 첨부 예정

</br>
</br>

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
<img width="625" alt="스크린샷 2024-01-12 오후 8 47 51" src="https://github.com/GuDonghee/Practice_Blog/assets/155864800/85ac7075-70aa-4811-825e-73df1ff1ec17">




</br>
</br>

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


</br>
</br>



## 인프라 구조
인프라는 ToyProject라는 시나리오로 개인이 사용한다는 가정아래 다음과 같이 구성하였습니다. 

기능을 확장하면서 시나리오를 변경하고 인프라를 확장할 예정입니다.

<img width="1320" alt="스크린샷 2024-01-16 오후 11 46 10" src="https://github.com/GuDonghee/Practice_Blog/assets/155864800/153188e4-5941-4cea-a848-21a3fa8959fb">



