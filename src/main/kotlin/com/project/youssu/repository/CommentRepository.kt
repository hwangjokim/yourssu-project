package com.project.youssu.repository

import com.project.youssu.domain.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CommentRepository : JpaRepository<Comment, Long> {
    fun save(comment: Comment) : Comment

    @Query("select c from Comment c join fetch c.article join fetch c.user where c.commentId = :id ")
    fun findByCommentId(@Param("id") commentId: Long) : Comment?
}