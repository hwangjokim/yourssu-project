package com.project.youssu.controller

import com.project.youssu.dto.ArticleRequest
import com.project.youssu.dto.ArticleResponse
import com.project.youssu.exception.IllegalException
import com.project.youssu.exception.IllegalRequestParamException
import com.project.youssu.service.ArticleService
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/article")
class ArticleController(private val service: ArticleService) {

    @PostMapping
    fun postArticle(@RequestBody @Valid request:ArticleRequest,
                    bindingResult: BindingResult,
                    servletRequest: HttpServletRequest) : ArticleResponse{
        if (bindingResult.hasErrors())
            throw IllegalRequestParamException(servletRequest, bindingResult)

        return service.saveArticle(request, servletRequest.requestURI)
    }

    @PutMapping("/{articleId}")
    fun updateArticle(@RequestBody @Valid request:ArticleRequest,
                      bindingResult: BindingResult,
                      servletRequest: HttpServletRequest,
                      @PathVariable articleId:Long) : ArticleResponse{
        if (bindingResult.hasErrors())
            throw IllegalRequestParamException(servletRequest, bindingResult)

        return service.updateArticle(request, servletRequest.requestURI, articleId)
    }
}