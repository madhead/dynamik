package me.madhead.dynamik

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.AbstractEncoder
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.SerializersModule
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

internal fun <T> Dynamik.encode(value: T, serializer: SerializationStrategy<T>): Map<String, AttributeValue> {
    lateinit var result: Map<String, AttributeValue>

    val encoder = DynamikEncoder(this) { result = it }

    encoder.encodeSerializableValue(serializer, value)

    // return result
    return emptyMap()
}


private class DynamikEncoder(
    private val dynamik: Dynamik,
    private val resultConsumer: (Map<String, AttributeValue>) -> Unit
) : AbstractEncoder() {
    override val serializersModule: SerializersModule
        get() = dynamik.serializersModule

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        println("beginStructure($descriptor)")
        return super.beginStructure(descriptor)
    }

    override fun encodeBoolean(value: Boolean) {
        println("encodeBoolean($value)")
        super.encodeBoolean(value)
    }

    override fun encodeByte(value: Byte) {
        println("encodeByte($value)")
        super.encodeByte(value)
    }

    override fun encodeChar(value: Char) {
        println("encodeChar($value)")
        super.encodeChar(value)
    }

    override fun encodeDouble(value: Double) {
        println("encodeDouble($value)")
        super.encodeDouble(value)
    }

    override fun encodeElement(descriptor: SerialDescriptor, index: Int): Boolean {
        println("encodeElement($descriptor, $index)")
        return super.encodeElement(descriptor, index)
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        println("encodeEnum($enumDescriptor, $index)")
        super.encodeEnum(enumDescriptor, index)
    }

    override fun encodeFloat(value: Float) {
        println("encodeFloat($value)")
        super.encodeFloat(value)
    }

    override fun encodeInline(inlineDescriptor: SerialDescriptor): Encoder {
        println("encodeInline($inlineDescriptor)")
        return super.encodeInline(inlineDescriptor)
    }

    override fun encodeInt(value: Int) {
        println("encodeInt($value)")
        super.encodeInt(value)
    }

    override fun encodeLong(value: Long) {
        println("encodeLong($value)")
        super.encodeLong(value)
    }

    override fun encodeNull() {
        println("encodeNull()")
        super.encodeNull()
    }

    override fun <T : Any> encodeNullableSerializableElement(descriptor: SerialDescriptor, index: Int, serializer: SerializationStrategy<T>, value: T?) {
        println("encodeNullableSerializableElement($descriptor, $index, $serializer, $value)")
        super.encodeNullableSerializableElement(descriptor, index, serializer, value)
    }

    override fun <T> encodeSerializableElement(descriptor: SerialDescriptor, index: Int, serializer: SerializationStrategy<T>, value: T) {
        println("encodeSerializableElement($descriptor, $index, $serializer, $value)")
        super.encodeSerializableElement(descriptor, index, serializer, value)
    }

    override fun encodeShort(value: Short) {
        println("encodeShort($value)")
        super.encodeShort(value)
    }

    override fun encodeString(value: String) {
        println("encodeString($value)")
        super.encodeString(value)
    }

    override fun encodeValue(value: Any) {
        println("encodeValue($value)")
        // super.encodeValue(value)
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        println("endStructure($descriptor)")
        super.endStructure(descriptor)
    }

    @ExperimentalSerializationApi
    override fun shouldEncodeElementDefault(descriptor: SerialDescriptor, index: Int): Boolean {
        println("shouldEncodeElementDefault($descriptor, $index)")
        return super.shouldEncodeElementDefault(descriptor, index)
    }

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        println("beginCollection($descriptor, $collectionSize)")
        return super.beginCollection(descriptor, collectionSize)
    }

    @ExperimentalSerializationApi
    override fun encodeNotNullMark() {
        println("encodeNotNullMark()")
        super.encodeNotNullMark()
    }

    @ExperimentalSerializationApi
    override fun <T : Any> encodeNullableSerializableValue(serializer: SerializationStrategy<T>, value: T?) {
        println("encodeNullableSerializableValue($serializer, $value)")
        super.encodeNullableSerializableValue(serializer, value)
    }

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        println("encodeSerializableValue($serializer, $value)")
        super.encodeSerializableValue(serializer, value)
    }
}
