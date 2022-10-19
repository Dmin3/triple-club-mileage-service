package com.example.tripleclubmileageservice.service

import com.example.tripleclubmileageservice.common.exception.ReviewAlreadyExistException
import com.example.tripleclubmileageservice.data.ReviewRequest
import com.example.tripleclubmileageservice.data.ReviewResponse
import com.example.tripleclubmileageservice.domain.forCreatePhoto
import com.example.tripleclubmileageservice.domain.forCreateReview
import com.example.tripleclubmileageservice.domain.forCreateUserPoint
import com.example.tripleclubmileageservice.repository.ReviewRepository
import com.example.tripleclubmileageservice.repository.UserPointHistoryRepository
import com.example.tripleclubmileageservice.repository.UserPointRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

interface ReviewService {
    fun create(reviewRequest: ReviewRequest): ReviewResponse
    fun update(reviewRequest: ReviewRequest): ReviewResponse
    fun delete(reviewRequest: ReviewRequest): ReviewResponse
}

@Service
@Transactional
class ReviewServiceImpl(
    private val reviewRepository: ReviewRepository,
    private val userPointRepository: UserPointRepository,
    private val userPointHistoryRepository: UserPointHistoryRepository
) : ReviewService {
    override fun create(reviewRequest: ReviewRequest): ReviewResponse {
        var point = 0

        // 사용자가 같은 장소에 리뷰를 작성 했는지 판단
        if (reviewRepository.existsByPlaceIdAndUserId(reviewRequest.placeId, reviewRequest.userId)) {
            throw ReviewAlreadyExistException("Already exist review")
        }

        // 장소에 대한 리뷰가 하나도 없다면 보너스 포인트 추가
        if (!reviewRepository.existsByPlaceId(reviewRequest.placeId)){
            point += 1
        }

        // 텍스트 작성 시 포인트 추가
        if (reviewRequest.content.isNotBlank()){
            point += 1
        }

        val review = forCreateReview(reviewRequest)

        // Photo Id가 존재하지 않다면 루프를 돌지 않고, 존재한다면 포인트 +1 증가
        if (reviewRequest.attachedPhotoIds.isNotEmpty()) {
            point += 1

            reviewRequest.attachedPhotoIds.forEach { photoId ->
                val createPhoto = forCreatePhoto(photoId)
                review.addPhoto(createPhoto)
            }
        }

        val saveReview = reviewRepository.save(review)

        if (point > 0){
            userPointRepository.save(forCreateUserPoint(point, saveReview.userId))
        }

        return saveReview.result()
    }



    override fun update(reviewRequest: ReviewRequest): ReviewResponse {
        TODO("Not yet implemented")
    }

    override fun delete(reviewRequest: ReviewRequest): ReviewResponse {
        TODO("Not yet implemented")
    }


}