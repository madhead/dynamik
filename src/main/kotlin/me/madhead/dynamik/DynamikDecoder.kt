package me.madhead.dynamik

import kotlinx.serialization.DeserializationStrategy
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

internal fun <T> Dynamik.decode(item: Map<String, AttributeValue>, deserializer: DeserializationStrategy<T>): T {
    TODO()
    // val input = when (item) {
    //     is JsonObject -> JsonTreeDecoder(this, element)
    //     is JsonArray -> JsonTreeListDecoder(this, element)
    //     is JsonLiteral, JsonNull -> JsonPrimitiveDecoder(this, element as JsonPrimitive)
    // }
    // return input.decodeSerializableValue(deserializer)
}

class DynamikDecoder {
}
