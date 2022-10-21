package com.example.tripleclubmileageservice.data

import com.example.tripleclubmileageservice.domain.UserPointType
import java.time.LocalDateTime
import java.util.*

data class UserPointHistoryResponse(
    val id : Long,
    val userId : UUID,
    val reviewId : UUID,
    val userPointType: UserPointType,
    val changePoint : Int,
    val createdAt : LocalDateTime
)
