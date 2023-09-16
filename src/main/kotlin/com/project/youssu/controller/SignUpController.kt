package com.project.youssu.controller

import com.project.youssu.dto.SignUpRequest
import com.project.youssu.dto.SignUpResponse
import com.project.youssu.exception.IllegalException
import com.project.youssu.service.SignUpService
import net.bytebuddy.pool.TypePool.Resolution.Illegal
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/signup")
class SignUpController(private val service: SignUpService) {

    @PostMapping
    fun signUp(@RequestBody @Valid request: SignUpRequest,
               bindingResult: BindingResult,
               servletRequest: HttpServletRequest) : SignUpResponse{

        val uri = servletRequest.requestURI

        if (bindingResult.hasErrors()){
            throw IllegalException(message = bindingResult.allErrors.toString(), uri)
        }
        return service.signUp(request, uri)
    }
}