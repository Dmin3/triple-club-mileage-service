package com.example.tripleclubmileageservice.repository

import com.example.tripleclubmileageservice.domain.Photo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface PhotoRepository : JpaRepository<Photo, UUID> {
}