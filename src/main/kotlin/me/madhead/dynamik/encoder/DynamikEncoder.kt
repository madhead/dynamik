package me.madhead.dynamik.encoder

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.descriptors.getContextualDescriptor
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.internal.NamedValueEncoder
import kotlinx.serialization.modules.SerializersModule
import me.madhead.dynamik.Dynamik
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

@InternalSerializationApi
class DynamikEncoder(
    val dynamik: Dynamik,
    private val consumer: (Map<String, AttributeValue>) -> Unit
) : NamedValueEncoder() {
    override val serializersModule: SerializersModule
        get() = dynamik.serializersModule

    private var polymorphicDiscriminator: String? = null

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        if (currentTagOrNull == null && serializer.descriptor.carrierDescriptor(serializersModule).requiresTopLevelTag)
            throw SerializationException("Serializing non-structured data (i.e. primitives) is not supported!")
        else {
            encodePolymorphically(serializer, value) { polymorphicDiscriminator = it }
        }
    }

    override fun encodeTaggedValue(tag: String, value: Any) {
        println("$tag: $value")
    }

    override fun endEncode(descriptor: SerialDescriptor) {
        consumer(mapOf("TEST" to AttributeValue.builder().s("TEST").build()))
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        println("BEGIN STRUCTURE")
        return super.beginStructure(descriptor)
    }

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        println("BEGIN COLLECTION")
        return super.beginCollection(descriptor, collectionSize)
    }
}

internal fun SerialDescriptor.carrierDescriptor(module: SerializersModule): SerialDescriptor = when {
    kind == SerialKind.CONTEXTUAL -> module.getContextualDescriptor(this)?.carrierDescriptor(module) ?: this
    isInline -> getElementDescriptor(0).carrierDescriptor(module)
    else -> this
}

private val SerialDescriptor.requiresTopLevelTag: Boolean
    get() = kind is PrimitiveKind || kind === SerialKind.ENUM
