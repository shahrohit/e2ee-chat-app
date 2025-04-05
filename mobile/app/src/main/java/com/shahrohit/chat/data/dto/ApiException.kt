package com.shahrohit.chat.data.dto

class ApiException (
    val statusCode : Int,
    override val message:String?,
    val errorData: Map<String, Any>? = null
) : Exception(message)
