package com.example.tripleclubmileageservice.domain

import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class Photo(
    @Id
    @Column(columnDefinition = "BINARY(16)")
    val id: UUID,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    var review: Review? = null,

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt: LocalDateTime? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Photo

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

fun forCreatePhoto(photoId: UUID): Photo {
    return Photo(
        id = photoId,
    )
}