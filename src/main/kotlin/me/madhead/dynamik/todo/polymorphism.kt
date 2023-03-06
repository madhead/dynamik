package me.madhead.dynamik.todo

import kotlinx.serialization.SealedClassSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.findPolymorphicSerializer
import kotlinx.serialization.internal.AbstractPolymorphicSerializer
import me.madhead.dynamik.Dynamik
import me.madhead.dynamik.DynamikClassDiscriminator
import me.madhead.dynamik.encoder.DynamikEncoder

@Suppress("UNCHECKED_CAST")
internal inline fun <T> DynamikEncoder.encodePolymorphically(
    serializer: SerializationStrategy<T>,
    value: T,
    ifPolymorphic: (String) -> Unit
) {
    if (serializer !is AbstractPolymorphicSerializer<*>) {
        serializer.serialize(this, value)
        return
    }
    val casted = serializer as AbstractPolymorphicSerializer<Any>
    val baseClassDiscriminator = serializer.descriptor.classDiscriminator(dynamik)
    val actualSerializer = casted.findPolymorphicSerializer(this, value as Any)

    validateIfSealed(casted, actualSerializer, baseClassDiscriminator)
    checkKind(actualSerializer.descriptor.kind)
    ifPolymorphic(baseClassDiscriminator)

    actualSerializer.serialize(this, value)
}

private fun SerialDescriptor.classDiscriminator(dynamik: Dynamik): String {
    for (annotation in annotations) {
        if (annotation is DynamikClassDiscriminator) return annotation.discriminator
    }

    return dynamik.configuration.classDiscriminator
}

private fun validateIfSealed(
    serializer: SerializationStrategy<*>,
    actualSerializer: SerializationStrategy<Any>,
    classDiscriminator: String
) {
    if (serializer !is SealedClassSerializer<*>) return

    if (classDiscriminator in actualSerializer.descriptor.serialNames()) {
        val baseName = serializer.descriptor.serialName
        val actualName = actualSerializer.descriptor.serialName

        throw IllegalStateException(
            "Sealed class '$actualName' cannot be serialized as base class '$baseName' because" +
                    " it has property name that conflicts with JSON class discriminator '$classDiscriminator'. " +
                    "You can either change class discriminator in DynamikConfiguration, " +
                    "or rename the property using @SerialName annotation"
        )
    }
}

private fun checkKind(kind: SerialKind) {
    if (kind is SerialKind.ENUM) {
        throw IllegalStateException("Enums cannot be serialized polymorphically.")
    }
    if (kind is PrimitiveKind) {
        throw IllegalStateException("Primitives cannot be serialized polymorphically.")
    }
    if (kind is PolymorphicKind) {
        throw IllegalStateException("Actual serializer for polymorphic cannot be polymorphic itself")
    }
}

private fun SerialDescriptor.serialNames(): Set<String> {
    val result = HashSet<String>(elementsCount)

    for (i in 0 until elementsCount) {
        result += getElementName(i)
    }

    return result
}
