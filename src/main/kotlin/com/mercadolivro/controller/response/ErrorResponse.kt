package com.mercadolivro.controller.response

data class ErrorResponse (
    val httpCode: Int,
    var message: String,
    var internalError: String,
    var error: List<FieldErrorResponse>?
)