package me.madhead.dynamik

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.SerializationException
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import me.madhead.dynamik.encoder.DynamikEncoder
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

/*
TODO
b // Bytes
bool // Boolean
bs // Binary Set
l // List
m // Map
n // Number
ns // Number set
nul // Null
s // String
ss // String set
*/

@InternalSerializationApi
sealed class Dynamik(
    val configuration: DynamikConfiguration,
    override val serializersModule: SerializersModule
) : SerialFormat {
    companion object Default : Dynamik(DynamikConfiguration(), EmptySerializersModule)

    /**
     * Serializes the given [value] into an equivalent [Map]<[String], [AttributeValue]> using the given [serializer].
     *
     * @throws [SerializationException] if the given value cannot be serialized.
     */
    fun <T> encode(serializer: SerializationStrategy<T>, value: T): Map<String, AttributeValue> {
        lateinit var result: Map<String, AttributeValue>
        val encoder = DynamikEncoder(this) { result = it }

        encoder.encodeSerializableValue(serializer, value)

        return result
    }

    /**
     * Serializes the given [value] into an equivalent [Map]<[String], [AttributeValue]> using a serializer, retrieved from the [serializersModule].
     *
     * @throws [SerializationException] if the given value cannot be serialized.
     */
    inline fun <reified T> encode(value: T): Map<String, AttributeValue> {
        return encode(serializersModule.serializer(), value)
    }

    // /**
    //  * Deserializes the given [item] into a value of type [T] using the given [deserializer].
    //  *
    //  * @throws [SerializationException] if the given item cannot be deserialized to the value of type [T].
    //  */
    // fun <T> decode(deserializer: DeserializationStrategy<T>, item: Map<String, AttributeValue>): T {
    //     return decode(item, deserializer)
    // }
    //
    // /**
    //  * Deserializes the given [item] into a value of type [T] using a serializer, retrieved from the [serializersModule].
    //  *
    //  * @throws [SerializationException] if the given item cannot be deserialized to the value of type [T].
    //  */
    // inline fun <reified T> decode(item: Map<String, AttributeValue>): T {
    //     return decode(serializersModule.serializer(), item)
    // }
    //
    // // TODO: TODO
    // fun <T> encodeToAttributeValue(serializer: SerializationStrategy<T>, value: T): AttributeValue {
    //     TODO()
    // }
    //
    // // TODO: TODO
    // inline fun <reified T> encodeToAttributeValue(value: T): Map<String, AttributeValue> {
    //     return encode(serializersModule.serializer(), value)
    // }
    //
    // fun nullAttributeValue(): AttributeValue = AttributeValue.builder().nul(true).build()
    // // TODO: Methods to serialize primitives to/from AttributeValue
}

fun Dynamik(
    from: Dynamik = Dynamik.Default,
    builderAction: DynamikBuilder.() -> Unit
): Dynamik {
    val builder = DynamikBuilder(from)

    builder.builderAction()

    val configuration = builder.build()

    return DynamikImpl(configuration, builder.serializersModule)
}

private class DynamikImpl(
    configuration: DynamikConfiguration,
    serializersModule: SerializersModule
) : Dynamik(configuration, serializersModule)
