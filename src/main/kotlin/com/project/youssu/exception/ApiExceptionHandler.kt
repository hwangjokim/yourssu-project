package com.project.youssu.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime



//정의해둔 Exception의 핸들러입니다.
@ControllerAdvice
class ApiExceptionHandler {
    @ExceptionHandler(IllegalException::class)
    fun illegalStateExceptionHandler(e : IllegalException) : ResponseEntity<ExceptionResponse>{
        val result = ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, e.message, e.uri)
        return ResponseEntity(result, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalRequestParamException::class)
    fun illegalRequestExceptionHandler(e: IllegalRequestParamException) : ResponseEntity<ExceptionResponse>{
        val uri = e.servletRequest.requestURI
        val builder= StringBuilder()
        if (e.bindingResult.hasErrors()){
            for(error in e.bindingResult.allErrors){
                val fe = error as FieldError
                builder.append("${fe.field} : ${error.defaultMessage} / ") //ex -> username : 유저네임은 빈칸일수 없습니다.
            }
        }
        val result = ExceptionResponse(LocalDateTime.now(), HttpStatus.BAD_REQUEST, builder.toString(), uri)
        return ResponseEntity(result, HttpStatus.BAD_REQUEST)
    }

}