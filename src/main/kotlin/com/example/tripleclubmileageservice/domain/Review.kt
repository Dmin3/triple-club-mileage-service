package com.example.tripleclubmileageservice.domain

import com.example.tripleclubmileageservice.data.ReviewRequest
import com.example.tripleclubmileageservice.data.ReviewResponse
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime
import java.util.*
import javax.persistence.*

@Entity
class Review(
    @Id
    @Column(name = "review_id" ,columnDefinition = "VARBINARY(16)")
    val id: UUID,

    @Column(columnDefinition = "VARBINARY(16)")
    val userId: UUID,

    @Column(columnDefinition = "VARBINARY(16)")
    val placeId: UUID,

    var content: String? = null,

    @OneToMany(mappedBy = "review", orphanRemoval = true, cascade = [CascadeType.ALL])
    var attachedPhotos : MutableList<Photo> = mutableListOf(),

    val createdAt: LocalDateTime = LocalDateTime.now(),

    var updatedAt : LocalDateTime? = null
) {

    fun addPhoto(photo: Photo){
        this.attachedPhotos.add(photo)
        photo.review = this
    }

    fun deletePhoto(photo: Photo) {
        this.attachedPhotos.remove(photo)
        photo.review = this
    }

    fun update(reviewRequest: ReviewRequest) {
        this.content = reviewRequest.content
        this.updatedAt = LocalDateTime.now()
    }

    fun result() : ReviewResponse {
        return ReviewResponse(
            reviewId = this.id,
            content = this.content,
            userId = this.userId,
            placeId = this.placeId,
            attachedPhotoIds = this.attachedPhotos.map { it.id }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Review

        if (id != other.id) return false


        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}

fun forCreateReview(reviewRequest: ReviewRequest) : Review{
    return Review(
        id = reviewRequest.reviewId,
        userId = reviewRequest.userId,
        placeId = reviewRequest.placeId,
        content = reviewRequest.content
    )
}