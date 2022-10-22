package com.example.tripleclubmileageservice.service

import com.example.tripleclubmileageservice.common.advice.exception.NotFoundException
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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

internal class ReviewServiceUpdateTest : ReviewServiceTest() {
    @BeforeEach
    fun setup() {
       setUp()
    }

    @Test
    fun `리뷰가 존재하지 않는다면 NotFoundException 예외 발생`() {
        //given
        val request = ReviewRequest(
            type = EventType.REVIEW,
            action = ReviewAction.MOD,
            content = "TEST",
            reviewId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            attachedPhotoIds = setOf(UUID.randomUUID())
        )

        //when
        every { reviewRepository.findById(any()) } returns Optional.empty()

        //then
        invoking { reviewServiceImpl.update(request) } shouldThrow NotFoundException::class
    }

    @Test
    fun `유저가 존재하지 않는다면 NotFoundException 예외 발생`() {
        //given
        val request = ReviewRequest(
            type = EventType.REVIEW,
            action = ReviewAction.MOD,
            content = "TEST",
            reviewId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            attachedPhotoIds = setOf(UUID.randomUUID())
        )

        every { reviewRepository.findById(any()) } returns Optional.of(forCreateReview(request))

        //when
        every { userPointRepository.findById(any()) } returns Optional.empty()

        //then
        invoking { reviewServiceImpl.update(request) } shouldThrow NotFoundException::class
    }

    @Test
    fun `글만 작성한 리뷰에 사진 추가 시 포인트 +1 점`() {
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
            action = ReviewAction.MOD,
            content = "TEST",
            reviewId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            attachedPhotoIds = setOf(UUID.randomUUID())
        )

        every { reviewRepository.findById(any()) } returns Optional.of(review)
        every { userPointRepository.findById(any()) } returns Optional.of(userPoint)
        every { userPointHistoryRepository.save(any()) } returns mockk()
        every { reviewRepository.save(any()).result() } returns review.result()

        //when
        reviewServiceImpl.update(request)

        //then
        verify(exactly = 1) { reviewServiceImpl.addPoint() }
    }

    @Test
    fun `글과 사진이 있는 리뷰에서 사진 모두 삭제 시 포인트 -1점`() {
        //given
        val userPoint = UserPoint(
            id = UUID.randomUUID(),
            point = 2
        )

        val photoId = UUID.randomUUID()

        val photo = Photo(
            id = photoId
        )

        val review = Review(
            id = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            content = "test",
            attachedPhotos = mutableListOf(photo),
        )

        photo.review = review

        val request = ReviewRequest(
            type = EventType.REVIEW,
            action = ReviewAction.MOD,
            content = "TEST",
            reviewId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            attachedPhotoIds = setOf(photoId)
        )

        every { reviewRepository.findById(any()) } returns Optional.of(review)
        every { userPointRepository.findById(any()) } returns Optional.of(userPoint)
        every { userPointHistoryRepository.save(any()) } returns mockk()
        every { photoRepository.findById(any()) } returns Optional.of(photo)
        every { reviewRepository.save(any()).result() } returns review.result()

        //when
        reviewServiceImpl.update(request)

        //then
        verify(exactly = 0) { reviewServiceImpl.addPoint() }
        verify(exactly = 1) { reviewServiceImpl.removePoint() }
    }

    @Test
    fun `리뷰 수정 시 content 업데이트`() {
        //given
        val userPoint = UserPoint(
            id = UUID.randomUUID(),
            point = 2
        )

        val review = Review(
            id = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            content = "TEST",
            attachedPhotos = mutableListOf(),
        )

        val request = ReviewRequest(
            type = EventType.REVIEW,
            action = ReviewAction.MOD,
            content = "UPDATE_TEST",
            reviewId = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            placeId = UUID.randomUUID(),
            attachedPhotoIds = setOf(UUID.randomUUID())
        )

        every { reviewRepository.findById(any()) } returns Optional.of(review)
        every { userPointRepository.findById(any()) } returns Optional.of(userPoint)
        every { userPointHistoryRepository.save(any()) } returns mockk()
        every { reviewRepository.save(any()) } returns review

        //when
        val updateReview = reviewServiceImpl.update(request)

        //then
        updateReview.content shouldBeEqualTo updateReview.content
    }
}