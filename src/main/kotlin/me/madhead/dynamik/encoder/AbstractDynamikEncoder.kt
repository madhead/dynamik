package me.madhead.dynamik.encoder

import kotlinx.serialization.SerializationException
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.internal.NamedValueEncoder
import kotlinx.serialization.modules.SerializersModule
import me.madhead.dynamik.Dynamik
import me.madhead.dynamik.todo.encodePolymorphically
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

internal abstract class AbstractDynamikEncoder(
    final override val dynamik: Dynamik,
    private val resultConsumer: (Map<String, AttributeValue>) -> Unit
) : NamedValueEncoder(), DynamikEncoder {
    final override val serializersModule: SerializersModule
        get() = dynamik.serializersModule

    @JvmField
    protected val configuration = dynamik.configuration

    private var polymorphicDiscriminator: String? = null

    override fun elementName(descriptor: SerialDescriptor, index: Int): String =
        descriptor.getElementName(index)

    override fun shouldEncodeElementDefault(descriptor: SerialDescriptor, index: Int): Boolean =
        configuration.encodeDefaults

    override fun composeName(parentName: String, childName: String): String = childName

    abstract fun putValue(key: String, value: AttributeValue)

    abstract fun getCurrent(): Map<String, AttributeValue>

    override fun encodeNotNullMark() {}

    // has no tag when encoding a nullable element at root level
    override fun encodeNull() {
        val tag = currentTagOrNull ?: return resultConsumer(emptyMap())

        encodeTaggedNull(tag)
    }

    override fun encodeTaggedNull(tag: String) = putValue(tag, AttributeValue.fromNul(true))

    override fun encodeTaggedByte(tag: String, value: Byte) = putValue(tag, AttributeValue.fromN(value.toString()))

    override fun encodeTaggedShort(tag: String, value: Short) = putValue(tag, AttributeValue.fromN(value.toString()))

    override fun encodeTaggedInt(tag: String, value: Int) = putValue(tag, AttributeValue.fromN(value.toString()))

    override fun encodeTaggedLong(tag: String, value: Long) = putValue(tag, AttributeValue.fromN(value.toString()))

    override fun encodeTaggedFloat(tag: String, value: Float) = putValue(tag, AttributeValue.fromN(value.toString()))

    override fun encodeTaggedDouble(tag: String, value: Double) = putValue(tag, AttributeValue.fromN(value.toString()))

    override fun <T> encodeSerializableValue(serializer: SerializationStrategy<T>, value: T) {
        if ((currentTagOrNull == null) && (serializer.descriptor.carrierDescriptor(serializersModule).kind !is StructureKind)) {
            // TODO
            throw SerializationException("TODO DESCRIPTION")
        }

        encodePolymorphically(serializer, value) { polymorphicDiscriminator = it }
    }

    override fun encodeTaggedBoolean(tag: String, value: Boolean) = putValue(tag, AttributeValue.fromBool(value))

    // TODO: Support string and codepoint chars!
    override fun encodeTaggedChar(tag: String, value: Char) = putValue(tag, AttributeValue.fromS(value.toString()))

    override fun encodeTaggedString(tag: String, value: String) = putValue(tag, AttributeValue.fromS(value))

    override fun encodeTaggedEnum(tag: String, enumDescriptor: SerialDescriptor, ordinal: Int) =
        putValue(tag, AttributeValue.fromS(enumDescriptor.getElementName(ordinal)))

    override fun encodeTaggedValue(tag: String, value: Any) = putValue(tag, AttributeValue.fromS(value.toString()))

//    override fun encodeTaggedInline(tag: String, inlineDescriptor: SerialDescriptor): Encoder =
//        when {
//            inlineDescriptor.isUnsignedNumber -> inlineUnsignedNumberEncoder(tag)
//            inlineDescriptor.isUnquotedLiteral -> inlineUnquotedLiteralEncoder(tag, inlineDescriptor)
//            else -> super.encodeTaggedInline(tag, inlineDescriptor)
//        }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
//        val consumer =
//            if (currentTagOrNull == null) resultConsumer
//            else { node -> putValue(currentTag, node) }
//
//        val encoder = when (descriptor.kind) {
//            StructureKind.LIST, is PolymorphicKind -> JsonTreeListEncoder(json, consumer)
//            StructureKind.MAP -> json.selectMapMode(
//                descriptor,
//                { JsonTreeMapEncoder(json, consumer) },
//                { JsonTreeListEncoder(json, consumer) }
//            )
//            else -> JsonTreeEncoder(json, consumer)
//        }
//
//        if (polymorphicDiscriminator != null) {
//            encoder.putElement(polymorphicDiscriminator!!, JsonPrimitive(descriptor.serialName))
//            polymorphicDiscriminator = null
//        }
//
//        return encoder

        println("beginStructure(\n\t$descriptor,\n)")
        return super.beginStructure(descriptor)
    }

    override fun endEncode(descriptor: SerialDescriptor) = resultConsumer(getCurrent())
}

internal fun SerialDescriptor.carrierDescriptor(module: SerializersModule): SerialDescriptor = when {
    kind == SerialKind.CONTEXTUAL -> module.getContextualDescriptor(this)?.carrierDescriptor(module) ?: this
    isInline -> getElementDescriptor(0).carrierDescriptor(module)
    else -> this
}

private val SerialDescriptor.requiresTopLevelTag: Boolean
    get() = kind is PrimitiveKind || kind === SerialKind.ENUM