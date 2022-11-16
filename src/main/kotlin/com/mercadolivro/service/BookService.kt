package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService (val bookRepository: BookRepository) {

    fun getAllBooks(name: String?): List<BookModel> {
        name?.let {
            return bookRepository.findByNameContaining(it)
        }
        return bookRepository.findAll().toList()
    }

    fun getAllActiveBooks(): List<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO)
    }

    fun getBookById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow()
    }

    fun createBook(book: BookModel) {
        bookRepository.save(book)
    }

    fun updateBook(book: BookModel) {
        bookRepository.save(book)
    }

    fun deleteBook(id: Int) {
        if (bookRepository.existsById(id)){
            val book = getBookById(id)
            book.status = BookStatus.CANCELADO
            updateBook(book)
        } else {
            throw Exception()
        }
    }

    fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomerId(customer)
        for (book in books) {
            book.status = BookStatus.APAGADO
        }
        bookRepository.saveAll(books)
    }

}