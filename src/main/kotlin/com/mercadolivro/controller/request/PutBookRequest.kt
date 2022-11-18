package com.mercadolivro.controller.request

import java.math.BigDecimal
import javax.validation.constraints.DecimalMin
import javax.validation.constraints.Digits

data class PutBookRequest (
    var name: String?,

    @field: DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser informado e maior que zero")
    @field: Digits(integer = 10, fraction = 2, message = "Ultrapassou valor maximo permitido")
    var price: BigDecimal?
)
