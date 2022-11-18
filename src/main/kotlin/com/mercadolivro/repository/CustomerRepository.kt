package com.mercadolivro.repository

import com.mercadolivro.enums.CustomerStatus
import com.mercadolivro.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CustomerRepository : JpaRepository<CustomerModel, Int> {

    fun findByNameContaining(name : String, pageable: Pageable): Page<CustomerModel>

    fun existsByEmail(email: String): Boolean

    fun findByStatus(status: CustomerStatus, pageable: Pageable): Page<CustomerModel>
}