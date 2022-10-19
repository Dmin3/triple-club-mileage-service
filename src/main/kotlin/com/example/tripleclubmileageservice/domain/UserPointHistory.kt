package com.example.tripleclubmileageservice.domain

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
    val id : Long,

    val userId: UUID,

    val userPointType: UserPointType,

    val point: Long,

    val createdAt: LocalDateTime = LocalDateTime.now()
){

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