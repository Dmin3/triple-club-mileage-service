package com.example.tripleclubmileageservice.service

import com.example.tripleclubmileageservice.common.data.Paginated
import com.example.tripleclubmileageservice.data.UserPointHistoryResponse
import com.example.tripleclubmileageservice.data.UserPointResponse
import com.example.tripleclubmileageservice.domain.Review
import com.example.tripleclubmileageservice.domain.UserPointType
import com.example.tripleclubmileageservice.domain.forCreatePointHistory
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.*

@Service
@Profile("test")
class UserPointServiceMock : UserPointService {
    override fun get(userId: UUID): UserPointResponse {
        return UserPointResponse(
            userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f745"),
            nowPoint = 7,
            usePoint = 0,
            accumulativePoint = 7
        )
    }

    override fun getUserByHistories(userId: UUID, pageable: Pageable): Paginated<UserPointHistoryResponse> {
        val review1 = Review(
            id = UUID.randomUUID(),
            userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f145"),
            content = "TEST",
            attachedPhotos = mutableListOf(),
            placeId = UUID.randomUUID()
        )

        val review2 = Review(
            id = UUID.randomUUID(),
            userId = UUID.fromString("3ede0ef2-92b7-4817-a5f3-0c575361f145"),
            content = "TEST",
            attachedPhotos = mutableListOf(),
            placeId = UUID.randomUUID()
        )

        val history1 = forCreatePointHistory(review1, 7, UserPointType.EARN).result()
        val history2 = forCreatePointHistory(review2, 3, UserPointType.EARN).result()

        val histories = listOf(history1, history2)
        val list = PageImpl(histories)

        return Paginated(histories, list)
    }

    override fun getAllHistories(pageable: Pageable): Paginated<UserPointHistoryResponse> {
        val review1 = Review(
            id = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            content = "TEST",
            attachedPhotos = mutableListOf(),
            placeId = UUID.randomUUID()
        )

        val review2 = Review(
            id = UUID.randomUUID(),
            userId = UUID.randomUUID(),
            content = "TEST",
            attachedPhotos = mutableListOf(),
            placeId = UUID.randomUUID()
        )

        val history1 = forCreatePointHistory(review1, 7, UserPointType.EARN).result()
        val history2 = forCreatePointHistory(review2, 3, UserPointType.EARN).result()

        val histories = listOf(history1, history2)
        val list = PageImpl(histories)

        return Paginated(histories, list)
    }
}