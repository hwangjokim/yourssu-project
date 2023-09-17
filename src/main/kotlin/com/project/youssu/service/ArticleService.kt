package com.project.youssu.service

import com.project.youssu.domain.Article
import com.project.youssu.domain.User
import com.project.youssu.dto.ArticleRequest
import com.project.youssu.dto.ArticleResponse
import com.project.youssu.dto.DeleteAndWithdrawDTO
import com.project.youssu.exception.ErrorMessage
import com.project.youssu.exception.IllegalException
import com.project.youssu.repository.ArticleRepository
import com.project.youssu.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional


@Service
@Transactional
class ArticleService(private val articleRepository: ArticleRepository,
                     private val userRepository: UserRepository,
                     private val encoder: PasswordEncoder) {

    fun saveArticle(request: ArticleRequest, uri:String) : ArticleResponse{
        val user = getUser(request, uri)
        val article = Article(0, LocalDateTime.now(), LocalDateTime.now(), request.content, request.title, user)
        val savedArticle = articleRepository.save(article)
        return ArticleResponse(savedArticle.articleId, user.email, savedArticle.title, savedArticle.content)
    }

    private fun getUser(request: ArticleRequest, uri: String): User {
        val user = userRepository.findByEmail(request.email)
        return user?.takeIf { encoder.matches(request.password, user.password) }
            ?: throw IllegalException(ErrorMessage.WRONG_USER_INFO.message, uri)
    }

    private fun getUser(request: DeleteAndWithdrawDTO, uri: String): User {
        val user = userRepository.findByEmail(request.email)
        return user?.takeIf { encoder.matches(request.password, user.password) }
            ?: throw IllegalException(ErrorMessage.WRONG_USER_INFO.message, uri)
    }

    fun updateArticle(request: ArticleRequest, uri:String, articleId:Long) : ArticleResponse{
        val user = getUser(request, uri)
        val article:Article = articleRepository.findByArticleId(articleId) ?: throw IllegalException(ErrorMessage.ARTICLE_NOT_FOUND.message, uri)
        //유저 정보 & 게시글 주인 매칭
        if (article.user != user)
            throw IllegalException(ErrorMessage.PERMISSION_DENIED.message, uri)

        article.title = request.title
        article.content = request.content
        article.updatedAt = LocalDateTime.now()

        return ArticleResponse(article.articleId, user.email, article.title, article.content)
    }

    fun deleteArticle(request: DeleteAndWithdrawDTO, uri: String, articleId: Long) : HttpStatus{
        val user = getUser(request, uri)
        val article:Article = articleRepository.findByArticleId(articleId) ?: throw IllegalException(ErrorMessage.ARTICLE_NOT_FOUND.message, uri)

        //유저 정보 & 게시글 주인 매칭
        if (article.user != user)
            throw IllegalException(ErrorMessage.PERMISSION_DENIED.message, uri)
        articleRepository.delete(article)
        return HttpStatus.OK
    }
}