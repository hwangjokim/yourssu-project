package com.project.youssu.service

import com.project.youssu.dto.SignUpRequest
import com.project.youssu.exception.IllegalException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.junit.platform.commons.logging.Logger
import org.junit.platform.commons.logging.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional

@SpringBootTest
@Transactional
class SignUpServiceTest() {
    @Autowired
    private lateinit var service: SignUpService
    private val log = org.slf4j.LoggerFactory.getLogger(javaClass)!!

    @Test
    fun signUp() {
        val newUser = SignUpRequest("yourssu@hwangjo.com", "mypassword" , "Hwangdo")

        val result = service.signUp(newUser, "/signup")

        log.debug("result : {}", result)

        Assertions.assertThat(result.email).isEqualTo("yourssu@hwangjo.com")
        Assertions.assertThat(result.username).isEqualTo("Hwangdo")

    }

    @Test
    fun duplicated(){

        val newUser = SignUpRequest("yourssu@hwangjo.com", "mypassword" , "Hwangdo")

        val result = service.signUp(newUser, "/signup")

        val second = SignUpRequest("dsaac@hwangjo.com", "dascs" , "Hwangdo")

        val error = org.junit.jupiter.api.Assertions.assertThrows(IllegalException::class.java){
            service.signUp(second, "/signup")
        }

        Assertions.assertThat(error.message).isEqualTo("중복된 사용자가 존재합니다.")

    }

}