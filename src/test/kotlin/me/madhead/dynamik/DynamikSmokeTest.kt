package me.madhead.dynamik

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import me.madhead.dynamik.types.Order
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Named.named
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import software.amazon.awssdk.enhanced.dynamodb.TableSchema

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DynamikSmokeTest {
    private lateinit var tableSchema: TableSchema<Order>
    private val json = Json

    @BeforeAll
    fun setUp() {
        tableSchema = TableSchema.fromBean(Order::class.java)
    }

    @ParameterizedTest
    @MethodSource("orders")
    @DisplayName("{1}")
    fun `serialize an Order`(
        order: Order
    ) {
        val expected = tableSchema.itemToMap(order, true)
        val actual = Dynamik.encode(order)

        assertEquals(expected, actual)
    }

    private fun orders(): Iterator<Arguments> = sequence {
        yield("001")
    }.map {
        arguments(named(it, json.decodeFromStream<Order>(this.javaClass.classLoader.getResourceAsStream("$it.json")!!)))
    }.iterator()
}
