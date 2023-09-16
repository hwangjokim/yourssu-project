package com.project.youssu.repository

import com.project.youssu.domain.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun save(user: User): User

    fun findByUsernameOrEmail(username: String, email: String): User?

    fun findByEmailAndPassword(email:String, password:String): User?


}