🏡 방도리 Bangdori - Backend
부동산 중개사 전용 매물 관리 플랫폼 **"방도리"**의 백엔드 레포지토리입니다.
Spring Boot 기반의 RESTful API 서버로, 회원 관리부터 매물 등록, 지도 기반 조회 등 다양한 기능을 제공합니다.


📌 목차

프로젝트 개요
기술 스택
프로젝트 구조
주요 기능
로컬 실행 방법
API 문서
DB 구조 및 ERD
배포 및 운영
협업 규칙
기타 참고 사항



📌 프로젝트 개요


목표: 공인중개사를 위한 매물 관리와 지도 기반 매물 조회를 지원하는 플랫폼

대상 사용자: 공인중개사

주요 기능: 로그인 / 매물 등록 및 조회 / 공지사항 / 지도 검색 / 기관별 관리 등



🛠️ 기술 스택


Backend: Spring Boot 3.x, Spring Security, JPA (Hibernate), MyBatis

Database: MySQL 8.x

Build Tool: Maven

Auth: JWT 기반 인증

ETC: Redis (선택적), Swagger / SpringDoc



🗂️ 프로젝트 구조