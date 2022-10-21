package com.example.tripleclubmileageservice.controller

import com.example.tripleclubmileageservice.common.data.Paginated
import com.example.tripleclubmileageservice.data.UserPointHistoryResponse
import com.example.tripleclubmileageservice.data.UserPointResponse
import com.example.tripleclubmileageservice.service.UserPointService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/points")
class UserPointApiController(
    private val userPointService: UserPointService
) {
    @GetMapping("/{userId}")
    fun get(@PathVariable userId : UUID) : ResponseEntity<UserPointResponse> {
        return ResponseEntity.ok(userPointService.get(userId))
    }

    @GetMapping("/histories/{userId}")
    fun getUserByHistories(@PathVariable userId: UUID, pageable: Pageable) : ResponseEntity<Paginated<UserPointHistoryResponse>>{
        return ResponseEntity.ok(userPointService.getUserByHistories(userId, pageable))
    }

    @GetMapping("/histories")
    fun getAllHistories(pageable: Pageable) : ResponseEntity<Paginated<UserPointHistoryResponse>>{
        return ResponseEntity.ok(userPointService.getAllHistories(pageable))
    }
}