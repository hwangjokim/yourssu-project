package com.project.youssu.domain

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class User(
    @Id
    @GeneratedValue
    val id: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val email: String,
    val password: String,
    val username: String,

    @OneToMany(mappedBy = "user", cascade = [CascadeType.REMOVE], orphanRemoval = true)
    val articles: MutableList<Article> = mutableListOf()

)
