package me.madhead.dynamik.encoding

import me.madhead.dynamik.Dynamik
import me.madhead.dynamik.encode
import me.madhead.dynamik.types.polymorphic.OwnedProject
import me.madhead.dynamik.types.polymorphic.Project
import org.junit.jupiter.api.Test

class PolymorphicTest {
    @Test
    fun `serialize StringHolder`() {
        val data: Project = OwnedProject("kotlinx.coroutines", "kotlin")
        println(Dynamik.encode(data))
    }
}
