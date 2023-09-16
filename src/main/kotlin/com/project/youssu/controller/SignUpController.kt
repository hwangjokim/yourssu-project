package com.project.youssu.controller

import com.project.youssu.dto.UserRequest
import com.project.youssu.dto.UserResponse
import com.project.youssu.exception.IllegalException
import com.project.youssu.exception.IllegalRequestParamException
import com.project.youssu.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/signup")
class SignUpController(private val service: UserService) {

    private val logger = LoggerFactory.getLogger(javaClass)!!

    @PostMapping
    fun signUp(@RequestBody @Valid request: UserRequest,
               bindingResult: BindingResult,
               servletRequest: HttpServletRequest) : UserResponse{

        if (bindingResult.hasErrors())
            throw IllegalRequestParamException(servletRequest, bindingResult)
        return service.signUp(request, servletRequest.requestURI)
    }
}