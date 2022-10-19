package com.example.tripleclubmileageservice.data

import java.util.*

data class ReviewResponse(
    val reviewId : UUID,
    val content : String?,
    val attachedPhotoIds : List<UUID> = mutableListOf(),
    val userId : UUID,
    val placeId: UUID
)
