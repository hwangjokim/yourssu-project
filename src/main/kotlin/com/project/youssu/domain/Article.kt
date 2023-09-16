package com.project.youssu.domain

import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Article(
    @Id
    @GeneratedValue
    val articleId: Long,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var content: String,
    var title: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User,
) {
    @OneToMany(mappedBy = "article", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val comments: MutableList<Comment> = mutableListOf()
}
