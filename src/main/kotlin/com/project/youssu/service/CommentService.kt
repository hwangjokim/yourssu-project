package com.project.youssu.service

import com.project.youssu.domain.Comment
import com.project.youssu.dto.DeleteAndWithdrawDTO
import com.project.youssu.dto.CommentRequest
import com.project.youssu.dto.CommentResponse
import com.project.youssu.exception.IllegalException
import com.project.youssu.repository.ArticleRepository
import com.project.youssu.repository.CommentRepository
import com.project.youssu.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
@Transactional
class CommentService(private val repository: CommentRepository,
                    private val articleRepository: ArticleRepository,
                    private val userRepository: UserRepository) {
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
        val comment = repository.findByCommentId(commentId) ?: throw IllegalException("댓글 번호가 잘못되었습니다.", uri)
        val user = getUser(request, uri)
        if (user != comment.user)
            throw IllegalException("권한이 없는 댓글입니다.", uri)
        if (comment.article.articleId != articleId)
            throw IllegalException("댓글과 게시글 위치가 일치하지 않습니다.", uri)

        comment.content = request.content
        comment.updatedAt = LocalDateTime.now()

        return CommentResponse(comment.commentId, user.email, comment.content)
    }

    fun deleteComment(request : DeleteAndWithdrawDTO, uri:String, articleId: Long, commentId: Long) : HttpStatus{
        val comment = repository.findByCommentId(commentId) ?: throw IllegalException("댓글 번호가 잘못되었습니다.", uri)
        val user = getUser(request, uri)
        if (user != comment.user)
            throw IllegalException("권한이 없는 댓글입니다.", uri)
        if (comment.article.articleId != articleId)
            throw IllegalException("댓글과 게시글 위치가 일치하지 않습니다.", uri)
        repository.delete(comment)
        return HttpStatus.OK
    }

    private fun getUser(request: CommentRequest, uri: String) =
        userRepository.findByEmailAndPassword(request.email, request.password)
            ?: throw IllegalException("유저 정보가 잘못되었습니다.", uri)

    private fun getUser(request: DeleteAndWithdrawDTO, uri: String) =
        userRepository.findByEmailAndPassword(request.email, request.password)
            ?: throw IllegalException("유저 정보가 잘못되었습니다.", uri)

    private fun getArticle(articleId: Long, uri: String) =
        articleRepository.findByArticleId(articleId) ?: throw IllegalException("존재하지 않는 게시글입니다.", uri)
}