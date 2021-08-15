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
  
## 의문
- [ ] @LoginMember 가 어떻게 가능한걸까?
- [ ] BaseEntity의 원리
- [ ] DB에 로그인 세션 자장하는 원리
- [ ] 메인화면의 url은 /blog, / 중 어떤게 좋을까?
- [ ] rebase와 reset 차이
- [ ] 동시에 http 요청이 들어오면??
- [x] 블로그 글 보여주는 목록이 보기 좋지 않다. 그냥 무작정 다 뿌려주기 때문인데.. 어떻게 바꿀까?
  최근 일주일 목록만 보여주도록 변경. 어차피 블로그가 많기 때문에 보여지기에 적은 데이터는 아니다.
- [ ] 메일 수신 여부 컨트롤을 위해 세션값을 강제로 바꾸어 주고 있는데, 이는 세션 값에 대한 착오를 일으킬 수도 있다.  
      어떻게 바꿀 수 있을지 고민 필요. 구독만 알림 하도록 개선할 때 고민 해보자.
- [x] 직렬화, 역직렬화, 병렬화 -> Serializable
  문제점 1. SessionMember에 Serializable가 왜 필요한가?
  문제점 2. SessionMember에서 mailReceiptStatus가 
  java.io.InvalidClassException: com.devlogmoa.config.auth.dto.SessionMember; local class incompatible: stream classdesc serialVersionUID = -8498973270536986012, local class serialVersionUID = -1124684850244601429
  -> 해결볍은 UID를 직접 생성하여 클래스 내용이 바뀌어도 버전이 자동생된 값으로 변경되지 않도록 사용.
     이는 java api 중 serialver.exe를 실행하면 자동으로 해당 클래스에 
  `private static final long serialVersionUID = -1124684850244601429L;` 와 같이 생성을 해준다.
  해당 프로젝트에 들어가서 serialver 클래스명 명령어를 치면 됨.
  갑자기 UID 없어도 잘되네?? 분명 메인화면 접속도 안됐는데..
  내 생각에는 에러가 발생했던 이유는 어떤 행위로 인해 SessionMember 직렬화가 되었고,
  나의 모든 컨트롤러는 SessionMember를 사용하기 때문에 클래스가 변경되어 그런 것 같다.
  근데 서버 재시작하면 다시 초기화 되어야 하는데 뭘까? 이전 SessionMember의 UID가 어딘가 남아있었나?
  
Member를 그대로 세션에 저장하지 않고, SessionMember Dto를 따로 만들어서 직렬화하였다.  
일단 세션은 네트워크이다. 즉, 직렬화가 필요하다. 그래서 Member에 직렬화를 하지 않고 세션에 저장하면
java.lang.IllegalArgumentException: DefaultSerializer requires a Serializable payload but received an object of type [com.devlogmoa.config.auth.dto.SessionMember]
이런 에러가 발생한다. 직렬화가 필수적이라는 것이다.
그렇다면 Member를 왜 직렬화하면 안되는가?
엔티티는 언제든 변경될 가능성이 있기 때문에 변경이 되면 세션에 있는 UID와 다른 UID를 가지기 때문에 역직렬화가 되지 않는다.
- [x] ROME 라이브러리도 rss xml 파싱을 하는데 에러발생  
  `Invalid XML: Error on line 512: 문서의 요소 콘텐츠에서 부적합한 XML 문자(유니코드: 0x1e)가 발견되었습니다.`  
  `RS`라인 개행문자가 들어있다. 이는 CRLF와 같은 것인데 각 OS및 버전별로 개행문자를 다른데 어쩌다 저런 문자가 들어간지는 알 수 없다.  
  windows는 CRLF가 개행문자이며, Unix(mac)은 LF이다.  
  <br>
  `Invalid XML: Error on line 3972: CDATA 섹션에서 부적합한 XML 문자(유니코드: 0x10)가 발견되었습니다.`
  CDATA 섹션에서 `LF`를 사용했기 때문에 나는 에러이다. 0x10은 LF라는데 어디서도 찾아볼 수 없다...  
  유스방에서 들었는데... ㅜ.ㅜ 유니코드와 아스크 등 이런 것들을 내가 잘 모른다. 공부필요.  
의문 1  
  - 해당 xml에는 CDATA를 쓰는 부분이 없는데 저런 에러가 발생한다.  
의문 2    
  - [ROME 라이브러리 개발자가 남긴 내용](https://github.com/rometools/rome/issues/449)을 보면 이스케이프 처리되지 않은 문자는 CDATA 섹션에서 지원하지 않는다고  
  했는데 CDATA를 사용하는 이유는 이스케이프 처리를 안하려고 하는거 아닌가?? 의문..
  
해결 : 일단 예외 잡아서 해당 데이터는 처리하지 않는 방향으로 함.