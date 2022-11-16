package com.mercadolivro.controller

import com.mercadolivro.extension.toCustomerModel
import com.mercadolivro.model.CustomerModel
import com.mercadolivro.request.PostCustomerRequest
import com.mercadolivro.request.PutCustomerRequest
import com.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerController(val customerService: CustomerService) {

    @GetMapping
    fun getAllCustomers(@RequestParam name: String?): List<CustomerModel> {
        return customerService.getAllCustomers(name)
    }

    @GetMapping("/{id}")
    fun getCustomerById(@PathVariable id: Int): CustomerModel {
        return customerService.getCustomerById(id)
    }

    @GetMapping("/active")
    fun getActiveCustomers(): List<CustomerModel> {
        return customerService.getActiveCustomers()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createCustomer(@RequestBody request: PostCustomerRequest) {
        customerService.createCustomer(request.toCustomerModel())
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun updateCustomer(@PathVariable id: Int, @RequestBody request: PutCustomerRequest) {
        val customer = customerService.getCustomerById(id)
        customerService.updateCustomer(request.toCustomerModel(customer))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCustomer(@PathVariable id: Int) {
        customerService.deleteCustomer(id)
    }

}