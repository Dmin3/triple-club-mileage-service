# triple-club-mileage-service
### 개요
트리플 사용자들이 장소에 리뷰를 작성할 때 포인트를 부여하고, 전체/개인에 대한 포인트 부여 히스토리와 개인별 누적 포인트를 관리하고자 합니다.

---

### 실행 방법
1. mysql 컨테이너를 실행합니다 (3306포트를 사용하고 있는지 확인해주세요.)
```
$ docker-compose up -d (백그라운드 실행)
```
2. 프로젝트를 빌드해주세요.
```
$ ./gradlew build
```
3. 프로젝트를 실행해주세요.
```
$ cd build/libs
$ java -jar triple-club-mileage-service-0.0.1-SNAPSHOT.jar
```

`POST http://localhost:8080/events`

4. 해당 주소로 데이터가 전송되는지 확인해주세요.
```JSON
{
"type": "REVIEW",
"action": "ADD", 
"reviewId": "240a0658-dc5f-4878-9381-ebb7b2667772",
"content": " !",
"attachedPhotoIds": ["e4d1a64e-a531-46de-88d0-ff0ed70c0bb8", "afb0cef2-851d-4a50-bb07-9cc15cbdc332"],
"userId": "3ede0ef2-92b7-4817-a5f3-0c575361f745",
"placeId": "2e4baf1c-5acb-4efb-a1af-eddada31b00f"
}
```
---

### 사용 기술 스택
- Kotlin 1.16.10
- Spring Boot 2.7.4
- Gradle 7.5
- JPA
- mysql 5.7
- docker-compose
- Spring REST Docs
- mockk , kluent (Test 라이브러리)

---

### ERD
- [schema.sql 확인](https://github.com/Dmin3/triple-club-mileage-service/blob/master/src/main/resources/schema.sql)

<img src="https://user-images.githubusercontent.com/80299170/197686712-d5c5e29d-dabe-49e1-a824-7a7b89e91c3d.png"  width="800" height="600"/>
