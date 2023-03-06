package me.madhead.dynamik.encoder

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.modules.EmptySerializersModule
import kotlinx.serialization.modules.SerializersModule

class TestEncoder : Encoder, CompositeEncoder {
    override val serializersModule: SerializersModule
        get() = EmptySerializersModule()

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        println("beginStructure(\n\t$descriptor\n)\n")
        return this
    }

    override fun encodeBoolean(value: Boolean) {
        println("encodeBoolean(\n\t$value\n)\n")
    }

    override fun encodeByte(value: Byte) {
        println("encodeByte(\n\t$value\n)\n")
    }

    override fun encodeChar(value: Char) {
        println("encodeChar(\n\t$value\n)\n")
    }

    override fun encodeDouble(value: Double) {
        println("encodeDouble(\n\t$value\n)\n")
    }

    override fun encodeEnum(enumDescriptor: SerialDescriptor, index: Int) {
        println("encodeEnum(\n\t$enumDescriptor,\n\t$index,\n)\n")
    }

    override fun encodeFloat(value: Float) {
        println("encodeFloat(\n\t$value\n)\n")
    }

    override fun encodeInline(descriptor: SerialDescriptor): Encoder {
        println("encodeInline(\n\t$descriptor\n)\n")
        return this
    }

    override fun encodeInt(value: Int) {
        println("encodeInt(\n\t$value\n)\n")
    }

    override fun encodeLong(value: Long) {
        println("encodeLong(\n\t$value\n)\n")
    }

    @ExperimentalSerializationApi
    override fun encodeNull() {
        println("encodeNull()\n")
    }

    override fun encodeShort(value: Short) {
        println("encodeShort(\n\t$value\n)\n")
    }

    override fun encodeString(value: String) {
        println("encodeString(\n\t$value\n)\n")
    }

    override fun encodeBooleanElement(descriptor: SerialDescriptor, index: Int, value: Boolean) {
        println("encodeBooleanElement(\n\t$descriptor,\n\t$index,\n\t$value,\n)\n")
    }

    override fun encodeByteElement(descriptor: SerialDescriptor, index: Int, value: Byte) {
        println("encodeByteElement(\n\t$descriptor,\n\t$index,\n\t$value,\n)\n")
    }

    override fun encodeCharElement(descriptor: SerialDescriptor, index: Int, value: Char) {
        println("encodeCharElement(\n\t$descriptor,\n\t$index,\n\t$value,\n)\n")
    }

    override fun encodeDoubleElement(descriptor: SerialDescriptor, index: Int, value: Double) {
        println("encodeDoubleElement(\n\t$descriptor,\n\t$index,\n\t$value,\n)\n")
    }

    override fun encodeFloatElement(descriptor: SerialDescriptor, index: Int, value: Float) {
        println("encodeFloatElement(\n\t$descriptor,\n\t$index,\n\t$value,\n)\n")
    }

    override fun encodeInlineElement(descriptor: SerialDescriptor, index: Int): Encoder {
        println("encodeInlineElement(\n\t$descriptor,\n\t$index,\n)\n")
        return this
    }

    override fun encodeIntElement(descriptor: SerialDescriptor, index: Int, value: Int) {
        println("encodeIntElement(\n\t$descriptor,\n\t$index,\n\t$value,\n)\n")
    }

    override fun encodeLongElement(descriptor: SerialDescriptor, index: Int, value: Long) {
        println("encodeLongElement(\n\t$descriptor,\n\t$index,\n\t$value,\n)\n")
    }

    @ExperimentalSerializationApi
    override fun <T : Any> encodeNullableSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        serializer: SerializationStrategy<T>,
        value: T?
    ) {
        println("encodeNullableSerializableElement(\n\t$descriptor,\n\t$index,\n\t$serializer,\n\t$value,\n)\n")
        encodeNullableSerializableValue(serializer, value)
    }

    override fun <T> encodeSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        serializer: SerializationStrategy<T>,
        value: T
    ) {
        println("encodeSerializableElement(\n\t$descriptor,\n\t$index,\n\t$serializer,\n\t$value,\n)\n")
        encodeSerializableValue(serializer, value)
    }

    override fun encodeShortElement(descriptor: SerialDescriptor, index: Int, value: Short) {
        println("encodeShortElement(\n\t$descriptor,\n\t$index,\n\t$value,\n)\n")
    }

    override fun encodeStringElement(descriptor: SerialDescriptor, index: Int, value: String) {
        println("encodeStringElement(\n\t$descriptor,\n\t$index,\n\t$value,\n)\n")
    }

    override fun endStructure(descriptor: SerialDescriptor) {
        println("endStructure(\n\t$descriptor,\n)\n")
    }

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        println("encodeSerializableValue(\n\t$serializer,\n\t$value,\n)\n")
        super.encodeSerializableValue(serializer, value)
    }

    override fun <T : Any> encodeNullableSerializableValue(serializer: SerializationStrategy<T>, value: T?) {
        println("encodeNullableSerializableValue(\n\t$serializer,\n\t$value,\n)\n")
        super.encodeNullableSerializableValue(serializer, value)
    }

    override fun encodeNotNullMark() {
        println("encodeNotNullMark()\n")
        super.encodeNotNullMark()
    }

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        println("beginCollection(\n\t$descriptor,\n\t$collectionSize\n)\n")
        return super.beginCollection(descriptor, collectionSize)
    }

    override fun shouldEncodeElementDefault(descriptor: SerialDescriptor, index: Int): Boolean {
        println("shouldEncodeElementDefault(\n\t$descriptor,\n\t$index\n)\n")
//        return super.shouldEncodeElementDefault(descriptor, index)
        return true
    }
}
