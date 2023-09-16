package com.project.youssu.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Comment(
    @Id
    @GeneratedValue
    val commentId: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val content: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne
    @JoinColumn(name = "article_id")
    val article: Article
)
