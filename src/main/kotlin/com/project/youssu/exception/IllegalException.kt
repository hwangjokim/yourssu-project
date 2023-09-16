package com.project.youssu.exception

import org.springframework.validation.ObjectError

// 비즈니스 로직 수행 중 발생한 예외를 담당합니다.
class IllegalException(message: String, val uri:String) : Exception(message) {
}