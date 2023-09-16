package com.project.youssu.dto

import javax.validation.constraints.NotBlank

data class UserRequest(
    @field: NotBlank(message = "이메일은 필수 항목입니다.")
    val email:String,
    @field: NotBlank(message = "비밀번호는 필수 항목입니다.")
    val password:String,
    @field: NotBlank(message = "유저네임은 필수 항목입니다.")
    val username:String,
)
