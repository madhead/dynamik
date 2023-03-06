package me.madhead.dynamik

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialFormat
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.serializer
import me.madhead.dynamik.decoder.DynamikDecoder
import me.madhead.dynamik.encoder.DynamikEncoder
import me.madhead.dynamik.encoder.TestEncoder
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

/**
 * The main entry point to work with DynamoDB's [items][AttributeValue] serialization.
 *
 * It is typically used by constructing an application-specific instance, with configured behaviour and, if necessary,
 * registered in [SerializersModule] custom serializers.
 *
 * `Dynamik` instance can be configured in its `Dynamik { }` factory function using [DynamikBuilder].
 * For demonstration purposes or trivial cases, Dynamik [companion][Dynamik.Default] can be used instead.
 *
 * Then constructed instance can be used either as regular [SerialFormat].
 *
 * Dynamik instance also exposes its [configuration] that can be used in custom serializers that rely on
 * [DynamikDecoder] and [DynamikEncoder] for customizable behaviour.
 */
sealed class Dynamik(
    val configuration: DynamikConfiguration,
    override val serializersModule: SerializersModule
) : SerialFormat {
    /**
     * The default instance of [Dynamik] with default configuration.
     */
    companion object Default : Dynamik(DynamikConfiguration(), EmptySerializersModule())

    /**
     * Serializes the given [value] into an equivalent [Map]<[String], [AttributeValue]> using the given [serializer].
     */
    fun <T> encode(serializer: SerializationStrategy<T>, value: T): Map<String, AttributeValue> {
        lateinit var result: Map<String, AttributeValue>

//        val encoder = DynamikObjectEncoder(this) { result = it }
        val encoder = TestEncoder()
//        val encoder = TestTaggedEncoder()

        encoder.encodeSerializableValue(serializer, value)

        return result
    }

    /**
     * Deserializes the given [item] into a value of type [T] using the given [deserializer].
     */
    fun <T> decode(deserializer: DeserializationStrategy<T>, item: Map<String, AttributeValue>): T {
        TODO()
    }
}

/**
 * Creates an instance of [Dynamik] configured from the optionally given [Dynamik instance][from] and adjusted
 * with the [builderAction].
 */
fun Dynamik(from: Dynamik = Dynamik.Default, builderAction: DynamikBuilder.() -> Unit): Dynamik {
    val builder = DynamikBuilder(from)

    builder.builderAction()

    val conf = builder.build()

    return DynamikImpl(conf, builder.serializersModule)
}

/**
 * Serializes the given [value] into an equivalent [Map]<[String], [AttributeValue]> using a serializer
 * retrieved from the reified type parameter.
 */
inline fun <reified T> Dynamik.encode(value: T): Map<String, AttributeValue> =
    encode(serializersModule.serializer(), value)

/**
 * Deserializes the given [item] element into a value of type [T] using a deserializer
 * retrieved from the reified type parameter.
 */
inline fun <reified T> Dynamik.decode(item: Map<String, AttributeValue>): T =
    decode(serializersModule.serializer(), item)

private class DynamikImpl(
    configuration: DynamikConfiguration,
    serializersModule: SerializersModule
) : Dynamik(configuration, serializersModule)
