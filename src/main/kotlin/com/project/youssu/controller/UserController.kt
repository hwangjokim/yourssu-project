package com.project.youssu.controller

import com.project.youssu.dto.DeleteAndWithdrawDTO
import com.project.youssu.dto.UserRequest
import com.project.youssu.dto.UserResponse
import com.project.youssu.exception.IllegalRequestParamException
import com.project.youssu.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/user")
class UserController(private val service: UserService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody @Valid request: UserRequest,
               bindingResult: BindingResult,
               servletRequest: HttpServletRequest) : UserResponse{

        if (bindingResult.hasErrors())
            throw IllegalRequestParamException(servletRequest, bindingResult)
        return service.signUp(request, servletRequest.requestURI)
    }

    @DeleteMapping("/withdraw")
    fun withdraw(@RequestBody @Valid request: DeleteAndWithdrawDTO,
                 bindingResult: BindingResult,
                 servletRequest: HttpServletRequest) : ResponseEntity<HttpStatus>{
        if (bindingResult.hasErrors())
            throw IllegalRequestParamException(servletRequest, bindingResult)

        return service.withdrawUser(request, servletRequest.requestURI)
    }
}