package com.mercadolivro.controller.request

import com.fasterxml.jackson.annotation.JsonAlias
import java.math.BigDecimal
import javax.validation.constraints.*

data class PostBookRequest (

    @field: NotEmpty(message = "Nome deve ser informado")
    var name: String,

    @field: DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser informado e maior que zero")
    @field: Digits(integer = 10, fraction = 2, message = "Ultrapassou valor maximo permitido")
    var price: BigDecimal,

    @JsonAlias("customer_id")
    var customerId: Int
)
