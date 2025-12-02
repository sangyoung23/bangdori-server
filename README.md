# 🏡 방도리 Bangdori - Backend
부동산 중개사 전용 매물 관리 플랫폼 "방도리"의 백엔드 레포지토리입니다.
Spring Boot 기반의 RESTful API 서버로, 회원 관리부터 매물 등록, 지도 기반 조회 등 다양한 기능을 제공합니다.

### 📌 프로젝트 개요


- **목표**: 공인중개사를 위한 매물 관리와 지도 기반 매물 조회를 지원하는 플랫폼
- **대상 사용자**: 공인중개사
- **주요 기능**: 로그인 / 매물 등록 및 조회 / 지도 검색 / 업체별 매물 관리 등




### 🛠️ 기술 스택


- **Backend**: Spring Boot, Spring Security, JPA (Hibernate)
- **Database**: MySQL
- **Build Tool**: Gradle
- **Auth**: JWT 기반 인증

### 📘 노션 API 문서

- https://bright-plum-a99.notion.site/11c49378bcad802c9b45fb3020e51b4e?pvs=4


### 🗂️ 프로젝트 구조

```bash
backend/
├── src/
│   ├── main/
│   │   ├── java/com/bangdori/api/
│   │   │   ├── comm/        # 공통 모듈 (유틸, 예외, 공통 응답 등)
│   │   │   ├── domain/
│   │   │   │   ├── code/    # 코드 관련 도메인
│   │   │   │   ├── product/ # 매물 관련 도메인
│   │   │   │   └── user/    # 사용자 및 기관 관련 도메인
│   │   │   └── ApiApplication.java # 메인 실행 클래스
│   │   └── resources/
│   │       ├── static/                # 정적 리소스
│   │       ├── application.yml        # 공통 설정
│   │       ├── application-local.yml  # 로컬 환경 설정
│   │       └── application-prod.yml   # 운영 환경 설정
│
├── test/
│   └── java/com/bangdori/api/
│       └── ApiApplicationTests.java   # 기본 테스트 클래스
│
├── .env                 # 환경 변수 파일
├── Dockerfile           # Docker 이미지 빌드를 위한 설정
├── build.gradle         # Gradle 빌드 스크립트
├── settings.gradle
├── gradlew / gradlew.bat
└── .gitignore
```

### 🗃 DB 구조 및 ERD

![스크린샷_2025-05-24_오후_10.40.25](/uploads/35e36258fb59d07bb90af04eb10a63a3/스크린샷_2025-05-24_오후_10.40.25.png)

### 🚀 배포 및 운영
#### 환경:
- 카페24 호스팅 서버 – SSD 가상서버 비즈니스 플랜
- OS: Ubuntu
- 웹 서버: Nginx
- 백엔드 애플리케이션은 Docker로 컨테이너화하여 배포

#### 운영 도메인
- www.bangdori.com
