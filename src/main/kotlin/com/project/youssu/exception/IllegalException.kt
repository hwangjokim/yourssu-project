package com.project.youssu.exception

import org.springframework.validation.ObjectError

class IllegalException(message: String, val uri:String) : Exception(message) {

//    fun getUri() : String{
//        return uri
//    }
}