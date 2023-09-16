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
        val user = getUser(request, uri)
        val article = Article(0, LocalDateTime.now(), LocalDateTime.now(), request.content, request.title, user)
        val savedArticle = articleRepository.save(article)
        return ArticleResponse(savedArticle.articleId, user.email, savedArticle.title, savedArticle.content)
    }

    private fun getUser(request: ArticleRequest, uri: String): User {
        return userRepository.findByEmailAndPassword(request.email, request.password)
            ?: throw IllegalException("사용자 정보가 일치하지 않습니다.", uri)
    }

    fun updateArticle(request: ArticleRequest, uri:String, articleId:Long) : ArticleResponse{
        val user = getUser(request, uri)
        val article:Article = articleRepository.findByArticleId(articleId) ?: throw IllegalException("게시글 번호가 유효하지 않습니다.", uri)
        //유저 정보 & 게시글 주인 매칭
        if (article.user != user)
            throw IllegalException("권한이 없는 게시글입니다.", uri)

        article.title = request.title
        article.content = request.content
        article.updatedAt = LocalDateTime.now()

        return ArticleResponse(article.articleId, user.email, article.title, article.content)

    }
}