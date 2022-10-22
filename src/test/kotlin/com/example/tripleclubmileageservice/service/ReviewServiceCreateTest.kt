package com.example.tripleclubmileageservice.service

import com.example.tripleclubmileageservice.common.advice.exception.ReviewAlreadyExistException
import com.example.tripleclubmileageservice.data.EventType
import com.example.tripleclubmileageservice.data.ReviewAction
import com.example.tripleclubmileageservice.data.ReviewRequest
import com.example.tripleclubmileageservice.domain.*
import com.example.tripleclubmileageservice.repository.PhotoRepository
import com.example.tripleclubmileageservice.repository.ReviewRepository
import com.example.tripleclubmileageservice.repository.UserPointHistoryRepository
import com.example.tripleclubmileageservice.repository.UserPointRepository
import io.mockk.*
import org.amshove.kluent.invoking
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldThrow
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.BeforeEach
import java.util.*

internal class ReviewServiceCreateTest : ReviewServiceTest() {
    @BeforeEach
    fun setup(){
        setUp()
    }

    @Test
    fun `사용자가 같은 장소에 리뷰를 작성 한다면 ReviewAlreadyExistException 예외 발생`() {
        //when
        val request = ReviewRequest(
            type = EventType.REVIEW,
            action = ReviewAction.ADD,
            content = "TEST",
            reviewId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            attachedPhotoIds = setOf(UUID.randomUUID())
        )

        //given
        every { reviewRepository.existsByPlaceIdAndUserId(request.placeId, request.userId) } returns true

        //then
        invoking { reviewServiceImpl.create((request)) } shouldThrow ReviewAlreadyExistException::class
    }

    @Test
    fun `첫 리뷰 + 글 작성 + 사진 리뷰 == 포인트 3점 이다`(){
        //given
        val request = ReviewRequest(
            type = EventType.REVIEW,
            action = ReviewAction.ADD,
            content = "TEST",
            reviewId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            attachedPhotoIds = setOf(UUID.randomUUID())
        )

        val review = forCreateReview(request)
        val userPoint = UserPoint(id = request.userId, point = 3, usePoint = 0, accumulativePoint = 3)

        every { reviewRepository.existsByPlaceIdAndUserId(any(),any()) } returns false
        every { reviewRepository.existsByPlaceId(any()) } returns false
        every { userPointRepository.findById(any()) } returns Optional.of(userPoint)
        every { userPointRepository.save(any()) } returns mockk()
        every { userPointHistoryRepository.save(any()) } returns mockk()
        every { reviewRepository.save(any()) } returns mockk()
        every { reviewRepository.save(any()).result() } returns review.result()

        //when
        reviewServiceImpl.create(request)

        //then
        //addPoint() 메소드가 3번 실행 되었다.
        verify(exactly = 3) { reviewServiceImpl.addPoint() }
    }

    @Test
    fun `첫 리뷰 + 사진 리뷰 == 포인트 2점 이다`(){
        //given
        val request = ReviewRequest(
            type = EventType.REVIEW,
            action = ReviewAction.ADD,
            content = "",
            reviewId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            attachedPhotoIds = setOf(UUID.randomUUID())
        )

        val review = forCreateReview(request)
        val userPoint = UserPoint(id = request.userId, point = 3, usePoint = 0, accumulativePoint = 3)

        every { reviewRepository.existsByPlaceIdAndUserId(any(),any()) } returns false
        every { reviewRepository.existsByPlaceId(any()) } returns false
        every { userPointRepository.findById(any()) } returns Optional.of(userPoint)
        every { userPointRepository.save(any()) } returns mockk()
        every { userPointHistoryRepository.save(any()) } returns mockk()
        every { reviewRepository.save(any()) } returns mockk()
        every { reviewRepository.save(any()).result() } returns review.result()

        //when
        reviewServiceImpl.create(request)

        //then
        //addPoint() 메소드가 2번 실행 되었다.
        verify(exactly = 2) { reviewServiceImpl.addPoint() }
        request.content.isNotBlank() shouldBeEqualTo false
    }

    @Test
    fun `사진 리뷰 + 글 작성 == 포인트 2점 이다`(){
        //given
        val request = ReviewRequest(
            type = EventType.REVIEW,
            action = ReviewAction.ADD,
            content = "TEST",
            reviewId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            attachedPhotoIds = setOf(UUID.randomUUID())
        )

        val review = forCreateReview(request)
        val userPoint = UserPoint(id = request.userId, point = 3, usePoint = 0, accumulativePoint = 3)

        every { reviewRepository.existsByPlaceIdAndUserId(any(),any()) } returns false
        every { userPointRepository.findById(any()) } returns Optional.of(userPoint)
        every { userPointRepository.save(any()) } returns mockk()
        every { userPointHistoryRepository.save(any()) } returns mockk()
        every { reviewRepository.save(any()) } returns mockk()
        every { reviewRepository.save(any()).result() } returns review.result()

        //when
        every { reviewRepository.existsByPlaceId(any()) } returns true
        reviewServiceImpl.create(request)

        //then
        //addPoint() 메소드가 2번 실행 되었다.
        verify(exactly = 2) { reviewServiceImpl.addPoint() }
    }
}


