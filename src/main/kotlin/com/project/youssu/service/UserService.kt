package com.project.youssu.service

import com.project.youssu.domain.User
import com.project.youssu.dto.DeleteAndWithdrawDTO
import com.project.youssu.dto.UserRequest
import com.project.youssu.dto.UserResponse
import com.project.youssu.exception.ErrorMessage
import com.project.youssu.exception.IllegalException
import com.project.youssu.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional


@Service
@Transactional
class UserService(private val repository: UserRepository, private val encoder: PasswordEncoder) {

    fun signUp(request : UserRequest, uri : String) : UserResponse{
        validateDuplication(request.username, request.email, uri)
        val saved = repository.save(
            User(
                0, // NOT USE_ GENERATED VALUE 사용합니다.
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now(),
                email = request.email,
                password = encoder.encode(request.password),
                username = request.username
            )
        )
        return UserResponse(saved.email, saved.username)
    }

    fun validateDuplication(username : String, email: String, uri: String){
        repository.findByUsernameOrEmail(username, email) ?. let {
            throw IllegalException(ErrorMessage.DUPLICATED_USER.message, uri)
        }
    }

    fun withdrawUser(request: DeleteAndWithdrawDTO, uri:String) : ResponseEntity<HttpStatus>{
        val user = repository.findByEmail(request.email)
        user?.takeIf { encoder.matches(request.password, user.password) }
            ?: throw IllegalException(ErrorMessage.WRONG_USER_INFO.message, uri)
        repository.delete(user)
        return ResponseEntity(HttpStatus.OK)
    }
}