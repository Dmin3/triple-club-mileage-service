package com.example.tripleclubmileageservice.service

import com.example.tripleclubmileageservice.common.advice.exception.NotFoundException
import com.example.tripleclubmileageservice.common.advice.exception.ReviewAlreadyExistException
import com.example.tripleclubmileageservice.data.ReviewRequest
import com.example.tripleclubmileageservice.data.ReviewResponse
import com.example.tripleclubmileageservice.domain.*
import com.example.tripleclubmileageservice.repository.PhotoRepository
import com.example.tripleclubmileageservice.repository.ReviewRepository
import com.example.tripleclubmileageservice.repository.UserPointHistoryRepository
import com.example.tripleclubmileageservice.repository.UserPointRepository
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface ReviewService {
    fun create(reviewRequest: ReviewRequest): ReviewResponse
    fun update(reviewRequest: ReviewRequest): ReviewResponse
    fun delete(reviewRequest: ReviewRequest)
}

@Service
@Profile("!test")
@Transactional
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val userPointRepository: UserPointRepository,
    private val userPointHistoryRepository: UserPointHistoryRepository,
    private val photoRepository: PhotoRepository
) : ReviewService {
    override fun create(reviewRequest: ReviewRequest): ReviewResponse {
        var point = 0

        // 사용자가 같은 장소에 리뷰를 작성 했는지 판단
        if (reviewRepository.existsByPlaceIdAndUserId(reviewRequest.placeId, reviewRequest.userId)) {
            throw ReviewAlreadyExistException("Already exist review")
        }

        // 장소에 대한 리뷰가 하나도 없다면 보너스 포인트 추가
        if (!reviewRepository.existsByPlaceId(reviewRequest.placeId)){
            point += addPoint()
        }

        // 텍스트 작성 시 포인트 추가
        if (reviewRequest.content.isNotBlank()){
            point += addPoint()
        }

        val review = forCreateReview(reviewRequest)

        // Photo Id가 존재하지 않다면 루프를 돌지 않고, 존재한다면 포인트 +1 증가
        if (reviewRequest.attachedPhotoIds.isNotEmpty()) {
            point += addPoint()

            reviewRequest.attachedPhotoIds.forEach { photoId ->
                val createPhoto = forCreatePhoto(photoId)
                review.addPhoto(createPhoto)
            }
        }

        if (point > 0) {
            val findUserPoint = userPointRepository.findById(reviewRequest.userId)

            if (findUserPoint.isPresent) {
                val userPoint = findUserPoint.get()
                userPoint.point += getPoint(point)
                userPoint.accumulativePoint += getPoint(point)
            } else {
                val userPoint = forCreateUserPoint(getPoint(point), reviewRequest.userId)
                userPointRepository.save(userPoint)
            }

            userPointHistoryRepository.save(forCreatePointHistory(review, getPoint(point), UserPointType.EARN))
        }

        val saveReview = reviewRepository.save(review)

        return saveReview.result()
    }

    override fun update(reviewRequest: ReviewRequest): ReviewResponse {
        val review = reviewRequest.reviewId?.let { findReview(reviewRequest.reviewId) }
            ?: throw NotFoundException("no exist Field reviewRequest.reviewId = null")

        val userPoint = findUserPoint(reviewRequest.userId)

        var point = 0

        // 리뷰사진이 없고 DTO에 사진이 있다면 포인트 +1 증가
        if (reviewRequest.attachedPhotoIds.isNotEmpty() && review.attachedPhotos.isEmpty()) {
            point += addPoint()
        }

        // 삭제할 사진 Id 와 추가할 사진 Id
        val existPhotoList = review.attachedPhotos.map { it.id }.toList()

        val deletePhotoIds = reviewRequest.attachedPhotoIds.filter { existPhotoList.contains(it) }.toList()
        val addPhotoIds = reviewRequest.attachedPhotoIds.filter { !existPhotoList.contains(it) }.toList()

        deletePhotoIds.forEach { photoId ->
            val photo = photoRepository.findById(photoId).get()
            review.deletePhoto(photo)
        }

        addPhotoIds.forEach { photoId ->
            review.addPhoto(forCreatePhoto(photoId))
        }

        review.update(reviewRequest)

        // 사진 삭제시 포인트 -1 감소
        if (review.attachedPhotos.isEmpty()) {
            point -= removePoint()
        }

        if (point > 0) {
            userPoint.point += getPoint(point)
            userPoint.accumulativePoint += getPoint(point)
            userPointHistoryRepository.save(forCreatePointHistory(review, getPoint(point), UserPointType.EARN))
        } else if (point < 0) {
            userPoint.point += getPoint(point)
            userPoint.accumulativePoint += getPoint(point)
            userPointHistoryRepository.save(forCreatePointHistory(review, getPoint(point), UserPointType.WITHDRAW))
        }

        val savedReview = reviewRepository.save(review)

        return savedReview.result()
    }

    override fun delete(reviewRequest: ReviewRequest) {
        val review = reviewRequest.reviewId?.let { findReview(reviewRequest.reviewId) }
            ?: throw NotFoundException("no exist Field reviewRequest.reviewId = null")

        val userPoint = findUserPoint(reviewRequest.userId)

        val pointHistories = userPointHistoryRepository.findByReviewIdAndUserId(review.id, userPoint.id)

        val sum = getUserPointHistories(pointHistories)

        userPoint.point -= sum

        userPointRepository.save(userPoint)
        userPointHistoryRepository.save(forCreatePointHistory(review, sum, UserPointType.WITHDRAW))

        reviewRepository.delete(review)
    }

    private fun findReview(reviewId: UUID): Review {
        val findReview = reviewRepository.findById(reviewId)
        return if (findReview.isEmpty) throw NotFoundException("not found : $reviewId") else findReview.get()
    }

    private fun findUserPoint(userId: UUID): UserPoint {
        val findUserPoint = userPointRepository.findById(userId)
        return if (findUserPoint.isEmpty) throw NotFoundException("not found : $userId") else findUserPoint.get()
    }

    internal fun getUserPointHistories(pointHistories: List<UserPointHistory>): Int {
        return pointHistories.sumOf { it.changePoint }
    }

    internal fun addPoint() = 1

    internal fun removePoint() = 1

    internal fun getPoint(point: Int): Int {
        return point
    }
}


