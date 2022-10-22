package com.example.tripleclubmileageservice.service

import com.example.tripleclubmileageservice.data.ReviewRequest
import com.example.tripleclubmileageservice.data.ReviewResponse
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.util.*

@Service
@Profile("test")
class ReviewServiceMock : ReviewService {

    val response = ReviewResponse(
        reviewId = UUID.fromString("240a0658-dc5f-4878-9381-ebb7b2667772"),
        content = "GOOD!",
        attachedPhotoIds = listOf(
            UUID.fromString("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8"),
            UUID.fromString("afb0cef2-851d-4a50-bb07-9cc15cbdc332")
        ),
        userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"),
        placeId = UUID.fromString("2e4baf1c-5acb-4efb-a1af-eddada31b00f")
    )

    override fun create(reviewRequest: ReviewRequest): ReviewResponse {
        return response
    }

    override fun update(reviewRequest: ReviewRequest): ReviewResponse {
        return response
    }

    override fun delete(reviewRequest: ReviewRequest) {

    }
}