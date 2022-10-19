package com.example.tripleclubmileageservice.data

import java.util.*

data class ReviewRequest(
    val type : EventType,
    val action : ReviewAction,
    val reviewId : UUID,
    val content : String,
    val attachedPhotoIds : Set<UUID> = mutableSetOf(),
    val userId : UUID,
    val placeId: UUID
)
