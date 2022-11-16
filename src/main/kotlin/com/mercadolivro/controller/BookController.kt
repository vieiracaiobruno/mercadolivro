package com.mercadolivro.controller

import com.mercadolivro.extension.toBookModel
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.request.PostBookRequest
import com.mercadolivro.request.PutBookRequest
import com.mercadolivro.request.PutCustomerRequest
import com.mercadolivro.service.BookService
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("books")
class BookController(val bookService: BookService, val customerService: CustomerService) {

    @GetMapping
    fun getAllBooks(@RequestParam name: String?): List<BookModel> {
        return bookService.getAllBooks(name)
    }

    @GetMapping("/active")
    fun getAllActiveBooks(): List<BookModel> {
        return bookService.getAllActiveBooks()
    }

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Int): BookModel {
        return bookService.getBookById(id)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createBook(@RequestBody request: PostBookRequest) {
        val customer = customerService.getCustomerById(request.customerId)
        bookService.createBook(request.toBookModel(customer))
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateBook(@PathVariable id: Int, @RequestBody request: PutBookRequest) {
        val book = bookService.getBookById(id)
        bookService.updateBook(request.toBookModel(book))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteBook(@PathVariable id: Int) {
        bookService.deleteBook(id)
    }
}