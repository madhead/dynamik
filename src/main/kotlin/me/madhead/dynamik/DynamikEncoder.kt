package me.madhead.dynamik

import kotlinx.serialization.SerializationStrategy
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

internal fun <T> Dynamik.encode(value: T, serializer: SerializationStrategy<T>): Map<String, AttributeValue> {
    return emptyMap()
    // TODO()
    lateinit var result: Map<String, AttributeValue>
    // val encoder = DynamikEncoder(this) { result = it }
    // encoder.encodeSerializableValue(serializer, value)
    return result
}


class DynamikEncoder {
}