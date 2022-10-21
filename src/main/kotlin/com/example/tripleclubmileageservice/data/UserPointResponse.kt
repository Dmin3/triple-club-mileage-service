package com.example.tripleclubmileageservice.data

import java.util.*

data class UserPointResponse(
    val userId : UUID,
    val nowPoint : Int,
    val usePoint : Int,
    val accumulativePoint : Int
)
