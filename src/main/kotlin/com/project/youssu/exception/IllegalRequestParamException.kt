package com.project.youssu.exception

import org.springframework.validation.BindingResult
import javax.servlet.http.HttpServletRequest


//Request 입력이 잘못되었을 경우를 담당합니다. (Validation 레벨 오류)
class IllegalRequestParamException(
    val servletRequest: HttpServletRequest,
    val bindingResult: BindingResult
) : Exception() {

}