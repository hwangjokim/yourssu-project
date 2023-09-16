package com.project.youssu.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime


@ControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(IllegalException::class)
    fun illegalStateExceptionHandler(e : IllegalException) : ResponseEntity<ExceptionResponse>{
        val result = ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.message, e.uri)
        return ResponseEntity(result, HttpStatus.BAD_REQUEST)
    }

}