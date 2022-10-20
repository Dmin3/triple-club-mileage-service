package com.example.tripleclubmileageservice.repository

import com.example.tripleclubmileageservice.domain.UserPointHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserPointHistoryRepository : JpaRepository<UserPointHistory, Long> {

    fun findByReviewIdAndUserId(reviewId : UUID, userId: UUID) : List<UserPointHistory>

}