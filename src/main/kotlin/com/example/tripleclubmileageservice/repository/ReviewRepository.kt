package com.example.tripleclubmileageservice.repository

import com.example.tripleclubmileageservice.domain.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewRepository : JpaRepository<Review, UUID> {
    fun existsByPlaceIdAndUserId(placeId : UUID, userId: UUID) : Boolean
    fun existsByPlaceId(placeId: UUID) : Boolean
}