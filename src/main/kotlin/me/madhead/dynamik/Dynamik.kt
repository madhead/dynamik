package me.madhead.dynamik

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
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

sealed class Dynamik(
    val configuration: DynamikConfiguration,
    override val serializersModule: SerializersModule
) : SerialFormat {
    fun <T> encode(serializer: SerializationStrategy<T>, value: T): Map<String, AttributeValue> {
        TODO()
    }

    fun <T> decode(deserializer: DeserializationStrategy<T>, item: Map<String, AttributeValue>): T {
        TODO()
    }

    companion object Default : Dynamik(DynamikConfiguration(), EmptySerializersModule)
}

public fun Dynamik(from: Dynamik = Dynamik.Default, builderAction: DynamikBuilder.() -> Unit): Dynamik {
    val builder = DynamikBuilder(from)

    builder.builderAction()

    val conf = builder.build()

    return DynamikImpl(conf, builder.serializersModule)
}

inline fun <reified T> Dynamik.encode(value: T): Map<String, AttributeValue> = encode(serializersModule.serializer(), value)

inline fun <reified T> Dynamik.decode(item: Map<String, AttributeValue>): T = decode(serializersModule.serializer(), item)

private class DynamikImpl(
    configuration: DynamikConfiguration,
    serializersModule: SerializersModule
) : Dynamik(configuration, serializersModule)
