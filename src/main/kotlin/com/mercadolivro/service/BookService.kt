package com.mercadolivro.service

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.exception.Errors
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.BookModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService (val bookRepository: BookRepository) {

    fun getAllBooks(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    fun getAllActiveBooks(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
    }

    fun getBookById(id: Int): BookModel {
        return bookRepository.findById(id).orElseThrow(){ NotFoundException(Errors.ML101.message.format(id), Errors.ML101.code)}
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