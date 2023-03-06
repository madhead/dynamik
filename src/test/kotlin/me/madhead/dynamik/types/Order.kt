package me.madhead.dynamik.types

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import me.madhead.dynamik.serializers.BigDecimalSerializer
import me.madhead.dynamik.serializers.InstantSerializer
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.math.BigDecimal
import java.time.Instant

/**
 * Mock class used to test various serialization aspects.
 */
@DynamoDbBean
@Serializable
data class Order(
    @get:DynamoDbPartitionKey
    var id: Long,

    var status: Status,

    @get:DynamoDbAttribute("price")
    @SerialName("price")
    @Serializable(BigDecimalSerializer::class)
    var totalPrice: BigDecimal,

    var currency: String = "EUR",

    var bonusCredits: Int = 0,

    var vat: Boolean = false,

    @Serializable(InstantSerializer::class)
    var date: Instant = Instant.now(),

    var items: List<OrderLineItem>,

    var tags: List<String> = emptyList(),
) {
    constructor() : this(
        id = 0L,
        status = Status.PENDING,
        totalPrice = 0.0.toBigDecimal(),
        items = emptyList(),
    )
}

@Serializable
enum class Status {
    PENDING,
    PAID,
    SHIPPED,
    COMPLETED,
}

@DynamoDbBean
@Serializable
data class OrderLineItem(
    var id: Long,

    var quantity: Int,

    @Serializable(BigDecimalSerializer::class)
    var price: BigDecimal,

    var currency: String = "EUR",

    var displayName: String,

    var displayNameTranslations: Map<String, String>? = null,
) {
    constructor() : this(
        id = 0L,
        quantity = 1,
        price = 0.0.toBigDecimal(),
        displayName = "",
    )
}
