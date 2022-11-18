package com.mercadolivro.controller

import com.mercadolivro.controller.request.PostCustomerRequest
import com.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.controller.response.CustomerResponse
import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.extension.toCustomerResponse
import com.mercadolivro.service.CustomerService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("customers")
class CustomerController(val customerService: CustomerService) {

    @GetMapping
    fun getAllCustomers(@RequestParam name: String?, @PageableDefault(page = 0, size = 10) pageable: Pageable): Page<CustomerResponse> {
        return customerService.getAllCustomers(name, pageable).map { it.toCustomerResponse() }
    }

    @GetMapping("/{id}")
    fun getCustomerById(@PathVariable id: Int): CustomerResponse {
        return customerService.getCustomerById(id).toCustomerResponse()
    }

    @GetMapping("/active")
    fun getActiveCustomers(@PageableDefault(page = 0, size = 10) pageable: Pageable): Page<CustomerResponse> {
        return customerService.getActiveCustomers(pageable).map { it.toCustomerResponse() }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody @Valid request: PostCustomerRequest) {
        customerService.createCustomer(request.toCustomerModel())
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateCustomer(@PathVariable id: Int, @RequestBody @Valid request: PutCustomerRequest) {
        val customer = customerService.getCustomerById(id)
        customerService.updateCustomer(request.toCustomerModel(customer))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Int) {
        customerService.deleteCustomer(id)
    }

}