package com.mercadolivro.model

import com.mercadolivro.enums.BookStatus
import com.mercadolivro.exception.BadRequestException
import com.mercadolivro.exception.Errors
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "book")
data class BookModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customerId: CustomerModel? = null
) {

    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null
        set(value) {
            if (field == BookStatus.CANCELADO || field == BookStatus.APAGADO) {
                throw BadRequestException(Errors.ML102.message.format(field), Errors.ML102.code)
            }
            field = value
        }

    constructor(id: Int? = null,
                name: String,
                price: BigDecimal,
                customerId: CustomerModel? = null,
                status: BookStatus? = null): this(id, name, price, customerId) {
                    this.status = status
                }
}