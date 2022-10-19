package com.example.tripleclubmileageservice.repository

import com.example.tripleclubmileageservice.domain.UserPointHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserPointHistoryRepository : JpaRepository<UserPointHistory, Long> {
}