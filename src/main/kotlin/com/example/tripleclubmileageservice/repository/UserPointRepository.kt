package com.example.tripleclubmileageservice.repository

import com.example.tripleclubmileageservice.domain.UserPoint
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserPointRepository : JpaRepository<UserPoint, UUID> {
}