package com.mercadolivro.extension

import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.enums.BookStatus
import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.request.PutCustomerRequest

fun PostCustomerRequest.toCustomerModel(): CustomerModel{
    return CustomerModel(name = this.name, email = this.email, status = CustomerStatus.ATIVO)
}

fun PutCustomerRequest.toCustomerModel(previous: CustomerModel): CustomerModel{
    return CustomerModel(id = previous.id, name = this.name, email = this.email, status = previous.status)
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel{
    return BookModel(name = this.name, price = this.price, status = BookStatus.ATIVO, customerId = customer)
}

fun PutBookRequest.toBookModel(previous: BookModel): BookModel{
    return BookModel(
        id = previous.id,
        name = this.name ?: previous.name,
        price = this.price ?: previous.price,
        status = previous.status,
        customerId = previous.customerId
    )
}
fun CustomerModel.toCustomerResponse(): CustomerResponse {
    return CustomerResponse(
        id = this.id,
        name = this.name,
        email = this.email,
        status = this.status
    )
}
fun BookModel.toBookResponse(): BookResponse {
    return BookResponse(
        id = this.id,
        name = this.name,
        price = this.price,
        customer = this.customerId,
        status = this.status
    )
}