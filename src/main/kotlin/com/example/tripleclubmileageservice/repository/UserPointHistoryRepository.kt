package com.example.tripleclubmileageservice.repository

import com.example.tripleclubmileageservice.domain.UserPointHistory
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserPointHistoryRepository : JpaRepository<UserPointHistory, Long> {

    fun findByReviewIdAndUserId(reviewId : UUID, userId: UUID) : List<UserPointHistory>
    fun findAllByUserId(userId: UUID, pageable: Pageable) : Page<UserPointHistory>

}