package me.madhead.dynamik.encoding

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.nullable
import kotlinx.serialization.builtins.serializer
import me.madhead.dynamik.Dynamik
import me.madhead.dynamik.types.Status
import org.junit.jupiter.api.Assertions.assertThrowsExactly
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.DynamicTest.dynamicTest
import org.junit.jupiter.api.TestFactory

class PrimitiveEncodingTest {
    @TestFactory
    fun tests(): Iterator<DynamicTest> = sequence {
        test(Byte.serializer().nullable, null)
        test(Byte.serializer(), 42.toByte())
        test(Short.serializer(), 42.toShort())
        test(Int.serializer(), 42)
        test(Long.serializer(), 42.toLong())
        test(Float.serializer(), 42.0F)
        test(Double.serializer(), 42.0)
        test(Boolean.serializer(), true)
        test(Char.serializer(), 'a')
        test(String.serializer(), "a")
        test(Status.serializer(), Status.PENDING)
        test(UByte.serializer(), 42.toUByte())
    }.iterator()

    private suspend fun <T> SequenceScope<DynamicTest>.test(serializer: KSerializer<T>, value: T) = yield(
        dynamicTest(value?.let { it::class.simpleName } ?: "Null") {
            assertThrowsExactly(SerializationException::class.java) {
                Dynamik.encode(serializer, value)
            }
        }
    )
}
