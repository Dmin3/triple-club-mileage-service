package com.example.tripleclubmileageservice.controller

import com.example.tripleclubmileageservice.data.ReviewAction
import com.example.tripleclubmileageservice.data.ReviewRequest
import com.example.tripleclubmileageservice.service.ReviewService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/events")
class ReviewApiController(
    private val reviewService: ReviewService
) {
    @PostMapping
    fun reviewEvents(@Valid @RequestBody reviewRequest: ReviewRequest): ResponseEntity<Any> {
        return when (reviewRequest.action) {
            ReviewAction.ADD -> {
                ResponseEntity.ok(reviewService.create(reviewRequest))
            }
            ReviewAction.MOD -> {
                ResponseEntity.ok(reviewService.update(reviewRequest))
            }
            ReviewAction.DELETE -> {
                ResponseEntity.ok(reviewService.delete(reviewRequest))
            }
        }
    }
}