package com.project.youssu.dto

import javax.validation.constraints.NotBlank

data class CommentRequest(
    @field: NotBlank
    val email:String,
    @field: NotBlank
    val password:String,
    @field: NotBlank
    val content:String
)
