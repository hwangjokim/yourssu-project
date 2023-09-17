package com.project.youssu.controller

import com.project.youssu.dto.CommentRequest
import com.project.youssu.dto.CommentResponse
import com.project.youssu.dto.DeleteAndWithdrawDTO
import com.project.youssu.exception.IllegalRequestParamException
import com.project.youssu.service.CommentService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid


@RestController
@RequestMapping("/article/{articleId}/comment")
class CommentController (private val service: CommentService){

    @PostMapping
    fun postComment(@RequestBody @Valid request: CommentRequest,
                    bindingResult: BindingResult,
                    servletRequest: HttpServletRequest,
                    @PathVariable articleId:Long) : CommentResponse{
        if (bindingResult.hasErrors())
            throw IllegalRequestParamException(servletRequest, bindingResult)
        return service.saveComment(request, servletRequest.requestURI, articleId)
    }

    @PutMapping("/{commentId}")
    fun updateComment(@RequestBody @Valid request: CommentRequest,
                      bindingResult: BindingResult,
                      servletRequest: HttpServletRequest,
                      @PathVariable articleId:Long,
                      @PathVariable commentId: Long) : CommentResponse{
        if (bindingResult.hasErrors())
            throw IllegalRequestParamException(servletRequest, bindingResult)
        return service.updateComment(request, servletRequest.requestURI, articleId, commentId)
    }

    @DeleteMapping("/{commentId}")
    fun deleteComment(@RequestBody @Valid request: DeleteAndWithdrawDTO,
                      bindingResult: BindingResult,
                      servletRequest: HttpServletRequest,
                      @PathVariable articleId:Long,
                      @PathVariable commentId: Long) : ResponseEntity<HttpStatus> {
        if (bindingResult.hasErrors())
            throw IllegalRequestParamException(servletRequest, bindingResult)
        service.deleteComment(request, servletRequest.requestURI, articleId, commentId)
        return ResponseEntity(HttpStatus.OK)
    }
}