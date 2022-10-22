package com.example.tripleclubmileageservice.common.advice

import com.example.tripleclubmileageservice.common.advice.exception.NotFoundException
import com.example.tripleclubmileageservice.common.advice.exception.NotMatchException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest

@RestControllerAdvice(basePackages = ["com.example.tripleclubmileageservice"])
class ErrorHandlingController {

    @ExceptionHandler(NotFoundException::class)
    fun notFoundException(e: NotFoundException, http: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse().apply {
            this.httpCode = HttpStatus.NOT_FOUND.value().toString()
            this.httpStatus = HttpStatus.NOT_FOUND
            this.path = http.requestURI.toString()
            this.message = e.message
            this.timeStamp = LocalDateTime.now()
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun httpMessageNotReadableException(e : Exception, http: HttpServletRequest) : ResponseEntity<ErrorResponse>{
        val errorResponse = ErrorResponse().apply {
            this.httpCode = HttpStatus.BAD_REQUEST.value().toString()
            this.httpStatus = HttpStatus.BAD_REQUEST
            this.path = http.requestURI.toString()
            this.message = e.message
            this.timeStamp = LocalDateTime.now()
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse)
    }
}