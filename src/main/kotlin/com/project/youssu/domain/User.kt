package com.project.youssu.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

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
)
