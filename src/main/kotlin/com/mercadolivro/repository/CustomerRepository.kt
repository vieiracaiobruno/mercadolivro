package com.mercadolivro.repository

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.CustomerModel
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<CustomerModel, Int> {

    fun findByNameContaining(name : String): List<CustomerModel>

    fun findByStatus(status: CustomerStatus): List<CustomerModel>
}