# triple-club-mileage-service
### 개요
트리플 사용자들이 장소에 리뷰를 작성할 때 포인트를 부여하고, 전체/개인에 대한 포인트 부여 히스토리와 개인별 누적 포인트를 관리하고자 합니다.

### 실행 방법
mysql 컨테이너를 실행합니다 (3306포트를 사용하고 있는지 확인해주세요.)
```
$ docker-compose up -d
```





### 사용 기술 스택
- Kotlin 1.16.10
- Spring Boot 2.7.4
- Gradle 7.5
- JPA
- mysql 5.7
- docker-compose
- Spring REST Docs
- mockk , kluent (Test 라이브러리)
