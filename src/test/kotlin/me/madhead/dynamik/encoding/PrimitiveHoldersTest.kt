package me.madhead.dynamik.encoding

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.madhead.dynamik.Dynamik
import me.madhead.dynamik.encode
import me.madhead.dynamik.types.primitive_holders.ByteArrayHolder
import me.madhead.dynamik.types.primitive_holders.StringHolder
import org.junit.jupiter.api.Test

class PrimitiveHoldersTest {
    @Test
    fun `serialize StringHolder`() {
        println(Dynamik.encode(StringHolder("test")))
    }
    @Test
    fun `serialize StringHolder1`() {
        println(Dynamik.encode(StringHolder("test", StringHolder("inner"))))
    }

    @Test
    fun `serialize ByteArrayHolder`() {
//        println(Dynamik.encode(ByteArrayHolder("test".toByteArray())))
        println(Json.encodeToString(ByteArrayHolder("test".toByteArray())))
    }
}
