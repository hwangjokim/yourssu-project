package com.project.youssu.exception

enum class ErrorMessage(val message: String) {
    WRONG_USER_INFO("사용자 정보가 일치하지 않습니다."),
    DUPLICATED_USER("중복된 사용자가 존재합니다."),
    ARTICLE_NOT_FOUND("존재하지 않는 게시글입니다."),
    COMMENT_NOT_FOUND("존재하지 않는 댓글입니다."),
    PERMISSION_DENIED("권한이 없습니다."),
    ARTICLE_COMMENT_NOT_MATCH("댓글과 게시글 위치가 일치하지 않습니다."),


}