package com.project.youssu.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Comment(
    @Id
    @GeneratedValue
    val commentId: Long,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var content: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    val article: Article
)
