package com.project.youssu.dto

import javax.validation.constraints.NotBlank

data class ArticleRequest(
    @field: NotBlank
    val email:String,
    @field: NotBlank
    val password:String,

    @field: NotBlank
    val title:String,
    @field: NotBlank
    val content:String
)
