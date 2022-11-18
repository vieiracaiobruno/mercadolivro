package com.mercadolivro.controller

import com.mercadolivro.extension.toBookModel
import com.mercadolivro.controller.request.PostBookRequest
import com.mercadolivro.controller.request.PutBookRequest
import com.mercadolivro.controller.response.BookResponse
import com.mercadolivro.extension.toBookResponse
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("books")
class BookController(val bookService: BookService, val customerService: CustomerService) {

    @GetMapping
    fun getAllBooks(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse> {
        return bookService.getAllBooks(pageable).map { it.toBookResponse() }
    }

    @GetMapping("/active")
    fun getAllActiveBooks(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<BookResponse> {
        return bookService.getAllActiveBooks(pageable).map { it.toBookResponse() }
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Int): BookResponse {
        return bookService.getBookById(id).toBookResponse()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody @Valid request: PostBookRequest) {
        val customer = customerService.getCustomerById(request.customerId)
        bookService.createBook(request.toBookModel(customer))
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateBook(@PathVariable id: Int, @RequestBody @Valid request: PutBookRequest) {
        val book = bookService.getBookById(id)
        bookService.updateBook(request.toBookModel(book))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id: Int) {
        bookService.deleteBook(id)
    }
}