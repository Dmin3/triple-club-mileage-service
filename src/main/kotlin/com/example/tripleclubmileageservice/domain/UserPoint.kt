package com.example.tripleclubmileageservice.domain

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class UserPoint(
    @Id
    @Column(columnDefinition = "BINARY(16)")
    val userId: UUID,

    var point: Int = 0,

    var usePoint : Int = 0,

    var accumulativePoint : Int = 0
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPoint

        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        return userId.hashCode()
    }
}

fun forCreateUserPoint(point: Int, userId: UUID) : UserPoint{
    return UserPoint(
        userId = userId,
        point = point,
        accumulativePoint = point
    )
}
