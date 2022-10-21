package com.example.tripleclubmileageservice.common.data

import org.springframework.data.domain.Page

data class Paginated<T>(
    val data: List<T>,
    val totalPages: Int,
    val totalElements: Long,
    val number: Int,
    val size: Int
){
    constructor(data: List<T>, page: Page<*>) : this(
        data = data,
        totalPages = page.totalPages,
        totalElements = page.totalElements,
        number = page.number,
        size = page.numberOfElements,
    )
}
