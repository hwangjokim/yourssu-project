package com.project.youssu.service

import com.project.youssu.domain.Article
import com.project.youssu.domain.User
import com.project.youssu.dto.ArticleRequest
import com.project.youssu.dto.ArticleResponse
import com.project.youssu.exception.IllegalException
import com.project.youssu.repository.ArticleRepository
import com.project.youssu.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional


@Service
@Transactional
class ArticleService(private val articleRepository: ArticleRepository,
                     private val userRepository: UserRepository) {

    fun saveArticle(request: ArticleRequest, uri:String) : ArticleResponse{
        val user = userRepository.findByEmailAndPassword(request.email, request.password)
            ?: throw IllegalException("사용자 정보가 일치하지 않습니다.", uri)
        val article = Article(0, LocalDateTime.now(), LocalDateTime.now(), request.content, request.title, user)
        val savedArticle = articleRepository.save(article)
        return ArticleResponse(savedArticle.articleId, user.email, savedArticle.title, savedArticle.content)
    }
}