package com.project.youssu.repository

import com.project.youssu.domain.Article
import org.springframework.data.jpa.repository.JpaRepository

interface ArticleRepository : JpaRepository<Article, Long> {
    fun save(article: Article) : Article

    fun findByArticleId(id: Long) : Article? //Update에 사용할 예정

}