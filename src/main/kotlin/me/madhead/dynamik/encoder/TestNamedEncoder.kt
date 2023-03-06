package me.madhead.dynamik.encoder

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.internal.NamedValueEncoder

class TestNamedEncoder : NamedValueEncoder() {
    @ExperimentalSerializationApi
    override fun shouldEncodeElementDefault(descriptor: SerialDescriptor, index: Int): Boolean {
        println("shouldEncodeElementDefault(\n\t$descriptor,\n\t$index,\n)\n")
        return super.shouldEncodeElementDefault(descriptor, index)
    }

    override fun beginCollection(descriptor: SerialDescriptor, collectionSize: Int): CompositeEncoder {
        println("beginCollection(\n\t$descriptor,\n\t$collectionSize,\n)\n")
        return super.beginCollection(descriptor, collectionSize)
    }

    @ExperimentalSerializationApi
    override fun <T : Any> encodeNullableSerializableValue(serializer: SerializationStrategy<T>, value: T?) {
        println("encodeNullableSerializableValue(\n\t$serializer,\n\t$value,\n)\n")
        super.encodeNullableSerializableValue(serializer, value)
    }

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        println("encodeSerializableValue(\n\t$serializer,\n\t$value,\n)\n")
        super.encodeSerializableValue(serializer, value)
    }

    override fun composeName(parentName: String, childName: String): String {
        println("composeName(\n\t$parentName,\n\t$childName,\n)\n")
        return super.composeName(parentName, childName)
    }

    override fun elementName(descriptor: SerialDescriptor, index: Int): String {
        println("elementName(\n\t$descriptor,\n\t$index,\n)\n")
        return super.elementName(descriptor, index)
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        println("beginStructure(\n\t$descriptor\n)\n")
        return super.beginStructure(descriptor)
    }

    override fun encodeNotNullMark() {
        println("encodeNotNullMark()\n")
        super.encodeNotNullMark()
    }

    override fun encodeNull() {
        println("encodeNull()\n")
        super.encodeNull()
    }

    override fun <T : Any> encodeNullableSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        serializer: SerializationStrategy<T>,
        value: T?
    ) {
        println("encodeNullableSerializableElement(\n\t$descriptor,\n\t$index,\n\t$serializer,\n\t$value,\n)\n")
        super.encodeNullableSerializableElement(descriptor, index, serializer, value)
    }

    override fun <T> encodeSerializableElement(
        descriptor: SerialDescriptor,
        index: Int,
        serializer: SerializationStrategy<T>,
        value: T
    ) {
        println("encodeSerializableElement(\n\t$descriptor,\n\t$index,\n\t$serializer,\n\t$value,\n)\n")
        super.encodeSerializableElement(descriptor, index, serializer, value)
    }

    override fun encodeTaggedBoolean(tag: String, value: Boolean) {
        println("encodeTaggedBoolean(\n\t$tag,\n\t$value,\n)\n")
        super.encodeTaggedBoolean(tag, value)
    }

    override fun encodeTaggedByte(tag: String, value: Byte) {
        println("encodeTaggedByte(\n\t$tag,\n\t$value,\n)\n")
        super.encodeTaggedByte(tag, value)
    }

    override fun encodeTaggedChar(tag: String, value: Char) {
        println("encodeTaggedChar(\n\t$tag,\n\t$value,\n)\n")
        super.encodeTaggedChar(tag, value)
    }

    override fun encodeTaggedDouble(tag: String, value: Double) {
        println("encodeTaggedDouble(\n\t$tag,\n\t$value,\n)\n")
        super.encodeTaggedDouble(tag, value)
    }

    override fun encodeTaggedEnum(tag: String, enumDescriptor: SerialDescriptor, ordinal: Int) {
        println("encodeEnum(\n\t$tag,\n\t$enumDescriptor,\n\t$ordinal,\n)\n")
        super.encodeTaggedEnum(tag, enumDescriptor, ordinal)
    }

    override fun encodeTaggedFloat(tag: String, value: Float) {
        println("encodeTaggedFloat(\n\t$tag,\n\t$value,\n)\n")
        super.encodeTaggedFloat(tag, value)
    }

    override fun encodeTaggedInline(tag: String, inlineDescriptor: SerialDescriptor): Encoder {
        println("encodeTaggedInline(\n\t$tag,\n\t$inlineDescriptor,\n)\n")
        return super.encodeTaggedInline(tag, inlineDescriptor)
    }

    override fun encodeTaggedInt(tag: String, value: Int) {
        println("encodeTaggedInt(\n\t$tag,\n\t$value,\n)\n")
        super.encodeTaggedInt(tag, value)
    }

    override fun encodeTaggedLong(tag: String, value: Long) {
        println("encodeTaggedLong(\n\t$tag,\n\t$value,\n)\n")
        super.encodeTaggedLong(tag, value)
    }

    override fun encodeTaggedNonNullMark(tag: String) {
        println("encodeTaggedNonNullMark(\n\t$tag,\n)\n")
        super.encodeTaggedNonNullMark(tag)
    }

    override fun encodeTaggedNull(tag: String) {
        println("encodeTaggedNull(\n\t$tag,\n)\n")
        super.encodeTaggedNull(tag)
    }

    override fun encodeTaggedShort(tag: String, value: Short) {
        println("encodeTaggedShort(\n\t$tag,\n\t$value,\n)\n")
        super.encodeTaggedShort(tag, value)
    }

    override fun encodeTaggedString(tag: String, value: String) {
        println("encodeTaggedString(\n\t$tag,\n\t$value,\n)\n")
        super.encodeTaggedString(tag, value)
    }

    override fun encodeTaggedValue(tag: String, value: Any) {
        println("encodeTaggedValue(\n\t$tag,\n\t$value,\n)\n")
//        super.encodeTaggedValue(tag, value)
    }

    override fun endEncode(descriptor: SerialDescriptor) {
        println("endEncode(\n\t$descriptor,\n)\n")
        super.endEncode(descriptor)
    }
}
