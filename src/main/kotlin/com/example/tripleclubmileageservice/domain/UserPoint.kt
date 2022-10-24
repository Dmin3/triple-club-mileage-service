package com.example.tripleclubmileageservice.domain

import com.example.tripleclubmileageservice.data.UserPointResponse
import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class UserPoint(
    @Id
    @Column(name = "user_id", columnDefinition = "VARBINARY(16)")
    val id: UUID,

    var point: Int = 0,

    var usePoint: Int = 0,

    var accumulativePoint: Int = 0
) {
    fun result(): UserPointResponse {
        return UserPointResponse(
            userId = this.id,
            nowPoint = this.point,
            usePoint = this.usePoint,
            accumulativePoint = this.accumulativePoint
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserPoint

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

fun forCreateUserPoint(point: Int, userId: UUID) : UserPoint{
    return UserPoint(
        id = userId,
        point = point,
        accumulativePoint = point
    )
}
