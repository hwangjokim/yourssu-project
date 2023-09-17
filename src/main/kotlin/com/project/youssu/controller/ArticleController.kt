package com.project.youssu.controller

import com.project.youssu.dto.ArticleRequest
import com.project.youssu.dto.ArticleResponse
import com.project.youssu.dto.DeleteAndWithdrawDTO
import com.project.youssu.exception.IllegalRequestParamException
import com.project.youssu.service.ArticleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
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

    @DeleteMapping("/{articleId}")
    fun deleteArticle(@RequestBody @Valid request: DeleteAndWithdrawDTO,
                      bindingResult: BindingResult,
                      servletRequest: HttpServletRequest,
                      @PathVariable articleId:Long) : ResponseEntity<HttpStatus> {
        if (bindingResult.hasErrors())
            throw IllegalRequestParamException(servletRequest, bindingResult)

        service.deleteArticle(request, servletRequest.requestURI, articleId)
        return ResponseEntity(HttpStatus.OK)
    }
}