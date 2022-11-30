package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.enums.Profile
import com.mercadolivro.exception.Errors
import com.mercadolivro.exception.NotFoundException
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class CustomerService (
    val customerRepository : CustomerRepository,
    val bookService: BookService,
    private val bCrypt: BCryptPasswordEncoder
) {

    fun getAllCustomers(name: String?, pageable: Pageable): Page<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it, pageable)
        }
        return customerRepository.findAll(pageable)
    }

    fun getCustomerById(id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow(){ NotFoundException(Errors.ML201.message.format(id), Errors.ML201.code)}
    }

    fun getActiveCustomers(pageable: Pageable): Page<CustomerModel> {
        return customerRepository.findByStatus(CustomerStatus.ATIVO, pageable)
    }

    fun createCustomer(customer: CustomerModel) {
        val customerCopy = customer.copy(
            roles = setOf(Profile.CUSTOMER),
            password = bCrypt.encode(customer.password)
        )
        customerRepository.save(customerCopy)
    }

    fun updateCustomer(customer: CustomerModel) {
        if (customerRepository.existsById(customer.id!!)){
            customerRepository.save(customer)
        } else {
            throw Exception()
        }
    }

//    fun deleteCustomer(id: Int) {
//        if (customerRepository.existsById(id)){
//            customerRepository.deleteById(id)
//        } else {
//            throw Exception()
//        }
//    }

    fun deleteCustomer(id: Int) {
        val customer = getCustomerById(id)
        bookService.deleteByCustomer(customer)
        customer.status = CustomerStatus.INATIVO
        customerRepository.save(customer)
    }

    fun emailAvailable(email: String): Boolean {
        return !customerRepository.existsByEmail(email)
    }

}