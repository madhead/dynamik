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
import java.util.Currency
import kotlin.random.Random
import kotlin.random.nextInt

/**
 * Mock class used to test various serialization aspects.
 */
@DynamoDbBean
@Serializable
data class Order(
    @get:DynamoDbPartitionKey
    var id: Long = 0L,

    var status: Status = Status.PENDING,

    @get:DynamoDbAttribute("price")
    @SerialName("price")
    @Serializable(BigDecimalSerializer::class)
    var totalPrice: BigDecimal = 0.0.toBigDecimal(),

    var currency: String = "EUR",

    var bonusCredits: Int = 0,

    var vat: Boolean = false,

    @Serializable(InstantSerializer::class)
    var date: Instant = Instant.now(),

    var items: List<OrderLineItem> = emptyList(),

    var tags: List<String> = emptyList(),
)

enum class Status {
    PENDING,
    PAID,
    SHIPPED,
    COMPLETED,
}

@DynamoDbBean
@Serializable
data class OrderLineItem(
    var id: Long = 0L,

    var quantity: Int = 0,

    @Serializable(BigDecimalSerializer::class)
    var price: BigDecimal = 0.0.toBigDecimal(),

    var currency: String = "EUR",

    var displayName: String = "",

    var displayNameTranslations: Map<String, String>? = null,
)

val Order.Companion.random: Order
    get() {
        val currency = CURRENCIES.random()
        val items: List<OrderLineItem> = List(Random.nextInt(5..15)) { OrderLineItem.random(currency) }

        return Order(
            id = Random.nextLong(),
            status = Status.values().random(),
            totalPrice = items.sumOf { it.price },
            currency = currency,
            bonusCredits = Random.nextInt(0..999),
            vat = Random.nextDouble() > 0.6,
            date = Instant.now(),
            items = items,
            tags = if (Random.nextDouble() > 0.7) {
                List(Random.nextInt(1..10)) { randomString(Random.nextInt(3..15)) }
            } else {
                emptyList()
            }
        )
    }

private val CURRENCIES = listOf("EUR", "USD")
private val LANGUAGES = mapOf(
    "EN" to 0.6,
    "ES" to 0.4,
    "DE" to 0.2,
    "IT" to 0.1
)

private val CHAR_POOL: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

private fun OrderLineItem.Companion.random(currency: String): OrderLineItem {
    return OrderLineItem(
        id = Random.nextLong(),
        quantity = Random.nextInt(1..6),
        price = Random.nextDouble(50.0).toBigDecimal(),
        currency = currency,
        displayName = randomString(Random.nextInt(10..50)),
        displayNameTranslations = if (Random.nextDouble() > 0.8) {
            buildMap {
                LANGUAGES.forEach { (language, probability) ->
                    if (Random.nextDouble() > (1 - probability)) {
                        this[language] = randomString(Random.nextInt(10..50))
                    }
                }
            }
        } else {
            null
        }
    )
}

private fun randomString(length: Int) = (1..length)
    .map { Random.nextInt(0, CHAR_POOL.size) }
    .map(CHAR_POOL::get)
    .joinToString("")
