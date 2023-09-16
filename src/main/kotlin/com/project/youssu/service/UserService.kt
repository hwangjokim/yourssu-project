package com.project.youssu.service

import com.project.youssu.domain.User
import com.project.youssu.dto.UserRequest
import com.project.youssu.dto.UserResponse
import com.project.youssu.exception.IllegalException
import com.project.youssu.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional


@Service
@Transactional
class UserService(private val repository: UserRepository) {

    fun signUp(request : UserRequest, uri : String) : UserResponse{
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
        return UserResponse(saved.email, saved.username)
    }

    fun validateDuplication(username : String, email: String, uri: String){
        repository.findByUsernameOrEmail(username, email) ?. let {
            throw IllegalException("중복된 사용자가 존재합니다.", uri)
        }
    }
}