[아이디어 제공](https://awesome-devblog.netlify.app/)  
**국내 개발 블로그의 정보를 한 곳에 모아서 보여주는 사이트를 만든다.**

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
awesome-devblog는 내가 구독하여 볼 수 있는 기능과 미리보기 기능이 없어 매번 해당 블로그로 직접 들어가야 한다.  
이런 부분이 아쉬웠다.  
그리하여, 이를 개선할 수도 있고, 실제로 내가 사용하고 운영해 볼 수 있는 서비스라고 생각하여,  
선정하게 되었다.

## MVP 기능

---
- [여기](https://github.com/sarojaba/awesome-devblog)서 국내 개발 블로그 RSS 데이터를 그대로 가져와서 보여준다.
- 각 블로그 최신 3개를 최신 순으로 보여준다.

## REAL 기능

---
### 공통
- 파싱해 온 데이터 중 각 블로그별로 체크하여 최신 데이터를 가공한다
  - DB에 적재된 데이터를 블로그별(link 태그) 구분하여 가장 최신에 작성된 글을 조 
  - 조회된 데이터를 파싱해온 데이터를 가장 최신 부터 비교하여  
    조회된 일자의 작성일 보다 파싱해 온 데이터의 작성일이 더 최신이면,  
    DB에 적재한다.
- 언제 작성된 내용인지 보여준다.
- 모든 블로그 기준 최신순으로 보여준다.
- 미리보기 기능

### 비로그인 사용자
- 운영자가 등록한 블로그만 볼 수 있다.
- 메인 페이지에서 본다#### 로그인 사용자
- 본인이 직접 블로그를 등록하여 볼 수 있다. 본인이 등록한 블로그 CRUD 가능
- 개인 페이지에서 본다.

### 관리자
- 블로그를 CRUD 가능

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
  
### 프론트 엔드
- thymeleaf : 2.4.5

### 주요 라이브러리
- Quartz : 2.4.5
- ROME : 1.0

