package com.example.tripleclubmileageservice.service

import com.example.tripleclubmileageservice.data.EventType
import com.example.tripleclubmileageservice.data.ReviewAction
import com.example.tripleclubmileageservice.data.ReviewRequest
import com.example.tripleclubmileageservice.domain.Review
import com.example.tripleclubmileageservice.domain.UserPoint
import com.example.tripleclubmileageservice.domain.UserPointType
import com.example.tripleclubmileageservice.domain.forCreatePointHistory
import com.example.tripleclubmileageservice.repository.PhotoRepository
import com.example.tripleclubmileageservice.repository.ReviewRepository
import com.example.tripleclubmileageservice.repository.UserPointHistoryRepository
import com.example.tripleclubmileageservice.repository.UserPointRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class ReviewServiceDeleteTest : ReviewServiceTest() {
    @BeforeEach
    fun setup() {
        setUp()
    }

    @Test
    fun `리뷰 삭제 시 포인트 기록 조회하여 리뷰 관련 포인트 모두 차감`() {
        //given
        val userPoint = UserPoint(
            id = UUID.randomUUID(),
            point = 2
        )

        val review = Review(
            id = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            content = "test",
            attachedPhotos = mutableListOf(),
        )

        val request = ReviewRequest(
            type = EventType.REVIEW,
            action = ReviewAction.DELETE,
            content = "TEST",
            reviewId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            attachedPhotoIds = setOf(UUID.randomUUID())
        )

        val histories = listOf(
            forCreatePointHistory(review, 1, UserPointType.EARN), forCreatePointHistory(review, 1, UserPointType.EARN)
        )

        every { reviewRepository.findById(any()) } returns Optional.of(review)
        every { userPointRepository.findById(any()) } returns Optional.of(userPoint)
        every { userPointHistoryRepository.findByReviewIdAndUserId(any(), any()) } returns histories
        every { userPointRepository.save(any()) } returns mockk()
        every { userPointHistoryRepository.save(any()) } returns mockk()
        every { reviewRepository.delete(any()) } returns mockk()

        //when
        reviewServiceImpl.delete(request)

        //then
        verify(exactly = 1) { reviewServiceImpl.getUserPointHistories(histories) }
        userPoint.point shouldBeEqualTo 0
    }
}