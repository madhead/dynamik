package me.madhead.dynamik.encoder

import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import me.madhead.dynamik.Dynamik
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

internal class DynamikObjectEncoder(
    dynamik: Dynamik,
    resultConsumer: (Map<String, AttributeValue>) -> Unit
) : AbstractDynamikEncoder(dynamik, resultConsumer) {
    protected val content: MutableMap<String, AttributeValue> = linkedMapOf()

    override fun putValue(key: String, value: AttributeValue) {
        content[key] = value
    }

    override fun <T : Any> encodeNullableSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        serializer: SerializationStrategy<T>,
        value: T?
    ) {
        if (value != null || configuration.explicitNulls) {
            super.encodeNullableSerializableElement(descriptor, index, serializer, value)
        }
    }

    override fun getCurrent(): Map<String, AttributeValue> = content
}
