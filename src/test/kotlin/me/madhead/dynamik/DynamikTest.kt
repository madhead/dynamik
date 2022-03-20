package me.madhead.dynamik

import me.madhead.dynamik.types.Order
import me.madhead.dynamik.types.OrderLineItem
import me.madhead.dynamik.types.Status
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import java.time.LocalDateTime
import java.time.ZoneOffset

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DynamikTest {
    private lateinit var tableSchema: TableSchema<Order>

    @BeforeAll
    fun setUp() {
        tableSchema = TableSchema.fromBean(Order::class.java)
    }

    @Test
    fun `serialize an Order`() {
        val order = Order(
            id = 42L,
            status = Status.PAID,
            totalPrice = 42.0.toBigDecimal(),
            currency = "EUR",
            bonusCredits = 10,
            vat = true,
            date = LocalDateTime.of(2012, 12, 12, 12, 12).toInstant(ZoneOffset.UTC),
            items = listOf(
                OrderLineItem(
                    id = 42L,
                    quantity = 42,
                    price = 21.0.toBigDecimal(),
                    currency = "EUR",
                    displayName = "Item #42",
                    displayNameTranslations = mapOf(
                        "DE" to "Item #42"
                    ),
                ),
                OrderLineItem(
                    id = 43L,
                    quantity = 43,
                    price = 21.0.toBigDecimal(),
                    currency = "EUR",
                    displayName = "Item #43",
                    displayNameTranslations = mapOf(
                        "DE" to "Item #43"
                    ),
                ),
            ),
            tags = listOf("tag1", "tag2"),
        )
        val expected = tableSchema.itemToMap(order, true)
        val actual = Dynamik.encode(order)

        assertEquals(expected, actual)
    }
}
