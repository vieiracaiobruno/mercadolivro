package com.mercadolivro.service

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService (val customerRepository : CustomerRepository, val bookService: BookService) {

    fun getAllCustomers(name: String?): List<CustomerModel> {
        name?.let {
            return customerRepository.findByNameContaining(it)
        }
        return customerRepository.findAll().toList()
    }

    fun getCustomerById(id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow()
    }

    fun getActiveCustomers(): List<CustomerModel> {
        return customerRepository.findByStatus(CustomerStatus.ATIVO).toList()
    }

    fun createCustomer(customer: CustomerModel) {
        customerRepository.save(customer)
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

}