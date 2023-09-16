package com.project.youssu.service

import com.project.youssu.dto.CommentRequest
import com.project.youssu.dto.DeleteAndWithdrawDTO
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Commit
import javax.transaction.Transactional


@SpringBootTest
@Transactional
class CommentServiceTest {

    @Autowired
    private lateinit var articleService: ArticleService
    @Autowired
    private lateinit var service: CommentService

    @Test
    @Commit
    fun saveComment() {
        val request = CommentRequest("yourssu@example.com","password", "댓글2입니다!")
        service.saveComment(request,"temp",16)
    }

    @Test
    @Commit
    fun updateComment() {
        val request = CommentRequest("yourssu@example.com","password", "수정댓글2입니다!")
        service.updateComment(request,"temp",16,24) //TODO: 쿼리 세번 나가는거 최적화

    }

    @Test
    @Commit
    fun deleteComment() {

        val request= DeleteAndWithdrawDTO("yourssu@example.com","password")
        service.deleteComment(request, "temp", 16, 24)
    }
}