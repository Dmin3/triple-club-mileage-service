package com.example.tripleclubmileageservice.common.advice

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ErrorResponse(
    var httpCode: String? = null,
    var httpStatus: HttpStatus? = null,
    var path: String? = null,
    var message: String? = null,
    var timeStamp: LocalDateTime? = null
)
