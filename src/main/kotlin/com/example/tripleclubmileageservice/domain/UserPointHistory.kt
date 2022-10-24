package com.example.tripleclubmileageservice.domain

import com.example.tripleclubmileageservice.data.UserPointHistoryResponse
import com.example.tripleclubmileageservice.data.UserPointResponse
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

enum class UserPointType(val number: Int, val typeName: String){
    EARN(0, "적립"),
    WITHDRAW(1, "회수")
}

@Entity
class UserPointHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(columnDefinition = "VARBINARY(16)")
    val userId: UUID,

    @Column(columnDefinition = "VARBINARY(16)")
    val reviewId: UUID,

    @Enumerated(EnumType.STRING)
    val userPointType: UserPointType,

    val changePoint: Int,

    val createdAt: LocalDateTime = LocalDateTime.now()
){
    fun result() : UserPointHistoryResponse{
        return UserPointHistoryResponse(
            id = this.id,
            userId = this.userId,
            reviewId = this.reviewId,
            userPointType = this.userPointType,
            changePoint = this.changePoint,
            createdAt = this.createdAt
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPointHistory

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

fun forCreatePointHistory(review: Review, point: Int, userPointType: UserPointType): UserPointHistory {
    return UserPointHistory(
        userId = review.userId,
        reviewId = review.id,
        changePoint = point,
        userPointType = userPointType
    )
}