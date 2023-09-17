package com.project.youssu.exception


// 비즈니스 로직 수행 중 발생한 예외를 담당합니다. Global
class IllegalException(message: String, val uri:String) : Exception(message) {
}