package com.example.tripleclubmileageservice.service

import com.example.tripleclubmileageservice.repository.PhotoRepository
import com.example.tripleclubmileageservice.repository.ReviewRepository
import com.example.tripleclubmileageservice.repository.UserPointHistoryRepository
import com.example.tripleclubmileageservice.repository.UserPointRepository
import io.mockk.mockk
import io.mockk.spyk

internal open class ReviewServiceTest {
    internal lateinit var reviewRepository: ReviewRepository
    internal lateinit var userPointRepository: UserPointRepository
    internal lateinit var userPointHistoryRepository: UserPointHistoryRepository
    internal lateinit var photoRepository: PhotoRepository
    internal lateinit var reviewServiceImpl: ReviewServiceImpl

    fun setUp() {
        reviewRepository = mockk(relaxed = true)
        userPointRepository = mockk(relaxed = true)
        userPointHistoryRepository = mockk(relaxed = true)
        photoRepository = mockk(relaxed = true)

        reviewServiceImpl = spyk(
            ReviewServiceImpl(
                reviewRepository = reviewRepository,
                userPointRepository = userPointRepository,
                userPointHistoryRepository = userPointHistoryRepository,
                photoRepository = photoRepository
            ), /* private 메소드 사용 가능 설정 */ recordPrivateCalls = true
        )
    }
}