package me.madhead.dynamik.encoding

import me.madhead.dynamik.Dynamik
import me.madhead.dynamik.encode
import me.madhead.dynamik.types.Order
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

class NullTest {
    @Test
    fun `serialize null`() {
        assertEquals(
            emptyMap<String, AttributeValue>(),
            Dynamik.encode(null as Order?)
        )
    }
}
