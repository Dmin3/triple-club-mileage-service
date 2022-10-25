## 프로젝트 구조
```
.
├── docs
│   └── asciidoc
│       └── index.adoc
├── main
│   ├── kotlin
│   │   └── com
│   │       └── example
│   │           └── tripleclubmileageservice
│   │               ├── TripleClubMileageServiceApplication.kt
│   │               ├── common
│   │               │   ├── advice
│   │               │   │   ├── ErrorHandlingController.kt
│   │               │   │   ├── ErrorResponse.kt
│   │               │   │   └── exception
│   │               │   │       ├── NotFoundException.kt
│   │               │   │       ├── NotMatchException.kt
│   │               │   │       └── ReviewAlreadyExistException.kt
│   │               │   └── data
│   │               │       └── Paginated.kt
│   │               ├── controller
│   │               │   ├── ReviewApiController.kt
│   │               │   └── UserPointApiController.kt
│   │               ├── data
│   │               │   ├── EventType.kt
│   │               │   ├── ReviewAction.kt
│   │               │   ├── ReviewRequest.kt
│   │               │   ├── ReviewResponse.kt
│   │               │   ├── UserPointHistoryResponse.kt
│   │               │   └── UserPointResponse.kt
│   │               ├── domain
│   │               │   ├── Photo.kt
│   │               │   ├── Review.kt
│   │               │   ├── UserPoint.kt
│   │               │   └── UserPointHistory.kt
│   │               ├── repository
│   │               │   ├── PhotoRepository.kt
│   │               │   ├── ReviewRepository.kt
│   │               │   ├── UserPointHistoryRepository.kt
│   │               │   └── UserPointRepository.kt
│   │               └── service
│   │                   ├── ReviewService.kt
│   │                   ├── ReviewServiceMock.kt
│   │                   ├── UserPointService.kt
│   │                   └── UserPointServiceMock.kt
│   └── resources
│       ├── application-local.yml
│       ├── application-test.yml
│       ├── application.yml
│       ├── data22.sql
│       ├── schema.sql
│       ├── static
│       │   └── docs
│       │       └── index.html
│       └── templates
└── test
    └── kotlin
        └── com
            └── example
                └── tripleclubmileageservice
                    ├── TripleClubMileageServiceApplicationTests.kt
                    ├── controller
                    │   ├── ReviewApiControllerTest.kt
                    │   └── UserPointApiControllerTest.kt
                    └── service
                        ├── ReviewServiceCreateTest.kt
                        ├── ReviewServiceDeleteTest.kt
                        ├── ReviewServiceTest.kt
                        ├── ReviewServiceUpdateTest.kt
                        └── UserPointServiceTest.kt

```
