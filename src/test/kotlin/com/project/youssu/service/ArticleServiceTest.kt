package com.project.youssu.service

import com.project.youssu.dto.ArticleRequest
import com.project.youssu.dto.DeleteAndWithdrawDTO
import com.project.youssu.exception.IllegalException
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import javax.transaction.Transactional


@SpringBootTest
@Transactional
class ArticleServiceTest {

    @Autowired
    private lateinit var service: ArticleService


    @Autowired
    private lateinit var userService: UserService

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

    @Test
    fun updateArticle(){
        var request:ArticleRequest = ArticleRequest("yourssu@example.com", "password", "제목입니다", "내용입니다")
        val result = service.saveArticle(request, "uri")
        var id = result.articleId

        request =  ArticleRequest("yourssu@example.com", "password", "제목2", "내용2") //정상 수정 테스트
        var updateArticle = service.updateArticle(request, "update", id)
        Assertions.assertThat(updateArticle.title).isEqualTo(request.title)
        Assertions.assertThat(updateArticle.content).isEqualTo(request.content)

        request =  ArticleRequest("yourssu@com", "password", "제목2", "내용2") //권한 테스트
        var error = org.junit.jupiter.api.Assertions.assertThrows(IllegalException::class.java){
            updateArticle = service.updateArticle(request, "update", id)
        }

        request =  ArticleRequest("yourssu@example.com", "password", "제목2", "내용2") //번호 이상
        error = org.junit.jupiter.api.Assertions.assertThrows(IllegalException::class.java){
            updateArticle = service.updateArticle(request, "update", 9999999)
        }


    }

    @Test
    fun delete(){
        var savedRequest:ArticleRequest = ArticleRequest("yourssu@example.com", "password", "제목입니다", "내용입니다")
        var result = service.saveArticle(savedRequest, "uri")

        val request = DeleteAndWithdrawDTO("yourssu@example.com","password")
        service.deleteArticle(request, "temp", result.articleId)
    }
}