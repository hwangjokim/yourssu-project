package com.project.youssu.service

import com.project.youssu.dto.ArticleRequest
import com.project.youssu.exception.IllegalException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.transaction.Transactional


@SpringBootTest
@Transactional
class ArticleServiceTest {

    @Autowired
    private lateinit var service: ArticleService


    @Test
    fun saveArticle() {
        var request:ArticleRequest = ArticleRequest("yourssu@example.com", "password", "제목입니다", "내용입니다")
        var result = service.saveArticle(request, "uri")
        Assertions.assertThat(result.title).isEqualTo(request.title)

        request = ArticleRequest("yourssu@example", "password", "제목입니다", "내용입니다") //유저정보 미스매치
        var error = org.junit.jupiter.api.Assertions.assertThrows(IllegalException::class.java){
            service.saveArticle(request, "uri")
        }
        Assertions.assertThat(error.message).isEqualTo("사용자 정보가 일치하지 않습니다.")

        request = ArticleRequest("yourssu@example", "password", "", "내용입니다") //내용 부재
        error = org.junit.jupiter.api.Assertions.assertThrows(IllegalException::class.java){
            service.saveArticle(request, "uri")
        }
    }
}