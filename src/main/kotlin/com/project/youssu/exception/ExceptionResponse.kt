package com.project.youssu.exception

import org.springframework.http.HttpStatus
import java.time.LocalDateTime


//예외 Response form 입니다.
data class ExceptionResponse(
    val time:LocalDateTime,
    val status:HttpStatus,
    val message:String?,
    val requestURI:String
)
