package com.project.youssu.service

import com.project.youssu.domain.User
import com.project.youssu.dto.SignUpRequest
import com.project.youssu.dto.SignUpResponse
import com.project.youssu.exception.IllegalException
import com.project.youssu.repository.SignUpRepository
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.LocalDateTime
import javax.servlet.http.HttpServletRequest


@Service
class SignUpService(private val repository: SignUpRepository) {

    fun signUp(request : SignUpRequest, uri : String) : SignUpResponse{
        validateDuplication(request.username, request.email, uri)
        val saved = repository.save(
            User(
                0, // NOT USE_ GENERATED VALUE 사용합니다.
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                email = request.email,
                password = request.password,
                username = request.username
            )
        )
        return SignUpResponse(saved.email, saved.username)
    }

    fun validateDuplication(username : String, email: String, uri: String){
        repository.findByUsernameOrEmail(username, email) ?. let {
            throw IllegalException("중복된 사용자가 존재합니다.", uri)
        }
    }
}