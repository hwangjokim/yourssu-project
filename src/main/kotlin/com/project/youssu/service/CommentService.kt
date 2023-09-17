package com.project.youssu.service

import com.project.youssu.domain.Comment
import com.project.youssu.domain.User
import com.project.youssu.dto.CommentRequest
import com.project.youssu.dto.CommentResponse
import com.project.youssu.dto.DeleteAndWithdrawDTO
import com.project.youssu.exception.ErrorMessage
import com.project.youssu.exception.IllegalException
import com.project.youssu.repository.ArticleRepository
import com.project.youssu.repository.CommentRepository
import com.project.youssu.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class CommentService(private val repository: CommentRepository,
                    private val articleRepository: ArticleRepository,
                    private val userRepository: UserRepository,
                    private val encoder: PasswordEncoder) {
    fun saveComment(request: CommentRequest, uri: String, articleId: Long): CommentResponse {
        val article = getArticle(articleId, uri)
        val user = getUser(request, uri)
        val comment = Comment(
            0,
            LocalDateTime.now(),
            LocalDateTime.now(),
            request.content,
            user,
            article
        )
        val savedComment = repository.save(comment)
        return CommentResponse(savedComment.commentId, user.email, savedComment.content)
    }

    fun updateComment(request: CommentRequest, uri: String, articleId: Long, commentId: Long) : CommentResponse{
        val comment = repository.findByCommentId(commentId) ?: throw IllegalException(ErrorMessage.COMMENT_NOT_FOUND.message, uri)
        val user = getUser(request, uri)
        if (user != comment.user)
            throw IllegalException(ErrorMessage.PERMISSION_DENIED.message, uri)
        if (comment.article.articleId != articleId)
            throw IllegalException(ErrorMessage.ARTICLE_COMMENT_NOT_MATCH.message, uri)

        comment.content = request.content
        comment.updatedAt = LocalDateTime.now()

        return CommentResponse(comment.commentId, user.email, comment.content)
    }

    fun deleteComment(request : DeleteAndWithdrawDTO, uri:String, articleId: Long, commentId: Long) : HttpStatus{
        val comment = repository.findByCommentId(commentId) ?: throw IllegalException(ErrorMessage.COMMENT_NOT_FOUND.message, uri)
        val user = getUser(request, uri)
        if (user != comment.user)
            throw IllegalException(ErrorMessage.PERMISSION_DENIED.message, uri)
        if (comment.article.articleId != articleId)
            throw IllegalException(ErrorMessage.ARTICLE_COMMENT_NOT_MATCH.message, uri)
        repository.delete(comment)
        return HttpStatus.OK
    }


    private fun getUser(request: CommentRequest, uri: String): User {
        val user = userRepository.findByEmail(request.email)
        return user?.takeIf { encoder.matches(request.password, user.password) }
            ?: throw IllegalException(ErrorMessage.WRONG_USER_INFO.message, uri)
    }
    private fun getUser(request: DeleteAndWithdrawDTO, uri: String): User {
        val user = userRepository.findByEmail(request.email)
        return user?.takeIf { encoder.matches(request.password, user.password) }
            ?: throw IllegalException(ErrorMessage.WRONG_USER_INFO.message, uri)
    }

    private fun getArticle(articleId: Long, uri: String) =
        articleRepository.findByArticleId(articleId) ?: throw IllegalException(ErrorMessage.ARTICLE_NOT_FOUND.message, uri)
}