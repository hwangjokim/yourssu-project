package com.project.youssu.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne

@Entity
data class Article(
    @Id
    @GeneratedValue
    val articleId: Long,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var content: String,
    var title: String,
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User
)
