package me.madhead.dynamik.encoder

import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.SealedClassSerializer
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.PolymorphicKind
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.SerialKind
import kotlinx.serialization.findPolymorphicSerializer
import kotlinx.serialization.internal.AbstractPolymorphicSerializer
import kotlinx.serialization.internal.jsonCachedSerialNames
import me.madhead.dynamik.Dynamik
import me.madhead.dynamik.DynamikClassDiscriminator

// TODO: Maybe inline?
@InternalSerializationApi
internal inline fun <T> DynamikEncoder.encodePolymorphically(
    serializer: SerializationStrategy<T>,
    value: T,
    ifPolymorphic: (String) -> Unit
) {
    if (serializer !is AbstractPolymorphicSerializer<*> || dynamik.configuration.useArrayPolymorphism) {
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

@InternalSerializationApi
private fun validateIfSealed(
    serializer: SerializationStrategy<*>,
    actualSerializer: SerializationStrategy<Any>,
    classDiscriminator: String
) {
    if (serializer !is SealedClassSerializer<*>) return
    @Suppress("DEPRECATION_ERROR")
    if (classDiscriminator in actualSerializer.descriptor.jsonCachedSerialNames()) {
        val baseName = serializer.descriptor.serialName
        val actualName = actualSerializer.descriptor.serialName
        error(
            "Sealed class '$actualName' cannot be serialized as base class '$baseName' because" +
                " it has property name that conflicts with JSON class discriminator '$classDiscriminator'. " +
                "You can either change class discriminator in JsonConfiguration, " +
                "rename property with @SerialName annotation or fall back to array polymorphism"
        )
    }
}

internal fun checkKind(kind: SerialKind) {
    if (kind is SerialKind.ENUM) error("Enums cannot be serialized polymorphically with 'type' parameter. You can use 'JsonBuilder.useArrayPolymorphism' instead")
    if (kind is PrimitiveKind) error("Primitives cannot be serialized polymorphically with 'type' parameter. You can use 'JsonBuilder.useArrayPolymorphism' instead")
    if (kind is PolymorphicKind) error("Actual serializer for polymorphic cannot be polymorphic itself")
}

// internal fun <T> JsonDecoder.decodeSerializableValuePolymorphic(deserializer: DeserializationStrategy<T>): T {
//     if (deserializer !is AbstractPolymorphicSerializer<*> || json.configuration.useArrayPolymorphism) {
//         return deserializer.deserialize(this)
//     }
//
//     val jsonTree = cast<JsonObject>(decodeJsonElement(), deserializer.descriptor)
//     val discriminator = deserializer.descriptor.classDiscriminator(json)
//     val type = jsonTree[discriminator]?.jsonPrimitive?.content
//     val actualSerializer = deserializer.findPolymorphicSerializerOrNull(this, type)
//         ?: throwSerializerNotFound(type, jsonTree)
//
//     @Suppress("UNCHECKED_CAST")
//     return json.readPolymorphicJson(discriminator, jsonTree, actualSerializer as DeserializationStrategy<T>)
// }

// private fun throwSerializerNotFound(type: String?, jsonTree: JsonObject): Nothing {
//     val suffix =
//         if (type == null) "missing class discriminator ('null')"
//         else "class discriminator '$type'"
//     throw JsonDecodingException(-1, "Polymorphic serializer was not found for $suffix", jsonTree.toString())
// }

@InternalSerializationApi
internal fun SerialDescriptor.classDiscriminator(dynamik: Dynamik): String {
    for (annotation in annotations) {
        if (annotation is DynamikClassDiscriminator) return annotation.discriminator
    }
    return dynamik.configuration.classDiscriminator
}
