[아이디어 제공](https://awesome-devblog.netlify.app/)  
**국내 개발 블로그의 정보를 한 곳에 모아서 보여주는 사이트를 만든다.**

[서비스 사이트 링크](http://www.devlogmoa.shop/)
[swagger ui 링크](http://www.devlogmoa.shop/swagger-ui.html)
## 만들게 된 이유

---
개발 관련 글들을 보고 싶은데 하나씩 찾아보기가 귀찮아서 보지 않고 있었다.  
하지만 항상 보고싶은 욕구가 있다. 개발을 잘하고 싶고, 흥미가 있기 때문이다.  
자주 찾아보지 않으면 흥미가 떨어질 것 같다는 생각도 드는 이유도 있다.  
아무래도 자주 보면 아는 것도 많아지기 때문에  
더 넓게 볼 수 있기 때문에 더 흥미를 유발할 수 있다고 생각하기 때문이다.

토이 프로젝트를 굉장히 여러가지 고민 하다가 Feedly와 같은 Rss 리더에 대해 알게 되었고,  
awesome-devblog라는 국내 개발 블로그 모음 사이틀를 알게 되었다.  
하지만 Feedly는 국내 개발 블로그를 모아 볼 수 있는 기능이 부족했고, 구독 시스템에 가까웠다.  
내가 모르는 블로그는 보기가 쉽지 않는 것이 단점이다.  
awesome-devblog는 내가 구독하여 볼 수 있는 기능이 아쉬웠다.  
그리하여, 이를 개선할 수도 있고, 실제로 내가 사용하고 운영해 볼 수 있는 서비스라고 생각했다.  
즉, awesome-devblog에서 구독 기능만 추가 될 예정이다.  
이후 운영하면서 하나 씩 발전 시킨다.

## MVP 기능

---
- rss를 가져와서 그대로 목록에 보여준다.

## REAL 기능

---
### 공통
- [여기](https://github.com/sarojaba/awesome-devblog)서 블로그 목록을 가져올 생각.
-   파싱해 온 데이터 중 각 블로그별로 체크하여 최신 데이터를 가공한다.
    -   DB에 적재된 데이터를 블로그별(link 태그) 구분하여 가장 최신에 작성된 글을 조회
    -   조회된 데이터를 파싱해온 데이터를 가장 최신 부터 비교하여  
        조회된 일자의 작성일 보다 파싱해 온 데이터의 작성일이 더 최신이면,  
        DB에 적재한다.
-   언제 작성된 내용인지 보여준다.
-   모든 블로그 기준 최신순으로 보여준다.
-   페이지당 10개 씩 보여준다.

### 비로그인 사용자
-   오직 블로그 게시글 읽기만 가능

### 로그인 사용자
-   구독이 가능하다. 해지도 가능
    구독을 하면 개인 페이지에서 구독 목록을 추려 보여준다.
-   구글 로그인

### 관리자
-   블로그 삭제 기능

## 프로그래밍 규칙

---
### Dto 규칙
- 단순히 조회만 하고, view 로직에 의해 Entity가 변경될 위험이 없다면  
  Dto가 필요 없을 수도 있다.  
  하지만 오히려 혼란을 만들 수도 있을 것 같아 무조건 ResponseDto, RequestDto를 생성한다.
  
## 개발 환경

---
### 인프라
- AWS EC2 : Amazon Linux 2 AMI (HVM), SSD Volume Type  
  => AMI(Amazon Machine Image)는 EC2 인스턴스를 시작하는데 **필요한 정보를 이미지로 만둘어 둔 것**
- RDS : Maria DB  
  선택이유  
  1. 저렴한 가격
  2. Amazon Aurora 교체의 용이성 : 월 10만원 기본 요금
     - RDS 보다 성능이 훨씬 좋다.
     - AWS에서 직접 관리하기 때문에 여러가지 기능이 있고, 계속 발전 중
     - MySQL, MariaDB, PostgreSQL을 지원 하기 때문에 굳이 MSSQL, 오라클을 선택하지 않는다.
  3. MySQL 보다 좋은 점
     - 향상된 성능
     - 활성화된 커뮤니티
     - 다양한 기능
     - 다양한 스토리지 엔진
  
### 빌드 툴
- gradle : 6.8.3

### 백엔드
- Spring Boot : 2.4.5
- JPA  
  => hibernate : 5.4.30.Final
- DB  
  => 운영 : MariaDB  
     jdbc client : 2.7.2
  => 로컬 : h2
- JAVA : 8 
- Lombok : 1.18.20
- querydsl : 4 ~~ 버전
  
### 프론트 엔드
- thymeleaf : 2.4.5

### 주요 라이브러리
- Quartz : 2.4.5
- ROME : 1.0

### 규약
- [naver conding convention](https://naver.github.io/hackday-conventions-java/)
- [rest api](https://meetup.toast.com/posts/92)

## ERD
- [ERD 링크](https://aquerytool.com:443/aquerymain/index/?rurl=6db53adb-a965-4fbc-b355-78db1561b21c)  
  비밀번호 : u4dn5b
  
## 이슈 사항!
- [블로그 정리](https://velog.io/@monkeydugi/devlogmoa-%EC%9D%B4%EC%8A%88)

## 커밋 규칙
- [앵귤러 규칙 참고](https://docs.google.com/document/d/1QrDFcIiPjSLDn3EL15IJygNPiHORgU1_OOAqWjiDU5Y/edit#heading=h.em2hiij8p46d)  
번역본
  - feat : 새로운 기능에 대한 커밋
  - fix : 버그 수정에 대한 커밋
  - build : 빌드 관련 파일 수정에 대한 커밋
  - chore : 그 외 자잘한 수정에 대한 커밋
  - ci : CI관련 설정 수정에 대한 커밋
  - docs : 문서 수정에 대한 커밋
  - style : 코드 스타일 혹은 포맷 등에 관한 커밋
  - refactor :  코드 리팩토링에 대한 커밋
  - test : 테스트 코드 수정에 대한 커밋  