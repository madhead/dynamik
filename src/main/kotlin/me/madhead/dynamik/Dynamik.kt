package me.madhead.dynamik

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.modules.SerializersModule
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

public sealed class Dynamik(
    override val serializersModule: SerializersModule,
) : SerialFormat {
    public fun <T> encode(serializer: SerializationStrategy<T>, value: T): Map<String, AttributeValue> {
        TODO()
    }

    public fun <T> decode(deserializer: DeserializationStrategy<T>, item: Map<String, AttributeValue>): T {
        TODO()
    }
}

// private class DynamikImpl(serializersModule: SerializersModule) : Dynamik(serializersModule)
//
// public fun Dynamik(module: SerializersModule): Dynamik = DynamikImpl(module)

// /**
//  * Encodes properties from given [value] to a map using serializer for reified type [T] and returns this map.
//  * `null` values are omitted from the output.
//  */
// @ExperimentalSerializationApi
// public inline fun <reified T> Properties.encodeToMap(value: T): Map<String, Any> =
//     encodeToMap(serializersModule.serializer(), value)
//
// /**
//  * Encodes properties from given [value] to a map using serializer for reified type [T] and returns this map.
//  * Converts all primitive types to [String] using [toString] method.
//  * `null` values are omitted from the output.
//  */
// @ExperimentalSerializationApi
// public inline fun <reified T> Properties.encodeToStringMap(value: T): Map<String, String> =
//     encodeToStringMap(serializersModule.serializer(), value)
//
// /**
//  * Decodes properties from given [map], assigns them to an object using serializer for reified type [T] and returns this object.
//  * [T] may contain properties of nullable types; they will be filled by non-null values from the [map], if present.
//  */
// @ExperimentalSerializationApi
// public inline fun <reified T> Properties.decodeFromMap(map: Map<String, Any>): T =
//     decodeFromMap(serializersModule.serializer(), map)
//
// /**
//  * Decodes properties from given [map], assigns them to an object using serializer for reified type [T] and returns this object.
//  * [String] values are converted to respective primitive types using default conversion methods.
//  * [T] may contain properties of nullable types; they will be filled by non-null values from the [map], if present.
//  */
// @ExperimentalSerializationApi
// public inline fun <reified T> Properties.decodeFromStringMap(map: Map<String, String>): T =
//     decodeFromStringMap(serializersModule.serializer(), map)
//
// // Migrations below
//
// @PublishedApi
// internal fun noImpl(): Nothing = throw UnsupportedOperationException("Not implemented, should not be called")