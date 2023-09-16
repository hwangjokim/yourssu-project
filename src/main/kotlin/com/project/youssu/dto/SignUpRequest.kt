package com.project.youssu.dto

import javax.validation.constraints.NotBlank

data class SignUpRequest(
    @NotBlank
    val email:String,
    @NotBlank
    val password:String,
    @NotBlank
    val username:String,
)
