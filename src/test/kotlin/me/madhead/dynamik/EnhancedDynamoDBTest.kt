package me.madhead.dynamik

import me.madhead.dynamik.types.Customer
import org.junit.jupiter.api.Test
import software.amazon.awssdk.enhanced.dynamodb.TableSchema
import java.time.Instant
import kotlin.random.Random

class EnhancedDynamoDBTest {
    @Test
    fun fff() {
        val tableSchema = TableSchema.fromBean(Customer::class.java)
        val item = Customer(
            id = Random.nextLong(),
            name = List(10) { (('a'..'z') + ('A'..'Z') + ('0'..'9')).random() }.joinToString(""),
            registrationDate = Instant.now(),
        )
        val map = tableSchema.itemToMap(item, true)

        println(map)
    }
}