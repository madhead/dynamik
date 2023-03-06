package me.madhead.dynamik.encoder

import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.internal.TaggedEncoder

class TestTaggedEncoder : TaggedEncoder<String>() {
    override fun SerialDescriptor.getTag(index: Int): String = this.getElementName(index)

    override fun encodeTaggedValue(tag: String, value: Any) {
        println("encodeTaggedValue(\n\t$tag,\n\t$value,\n)\n")
    }

    override fun beginStructure(descriptor: SerialDescriptor): CompositeEncoder {
        println("beginStructure(\n\t$descriptor\n)\n")
        return super.beginStructure(descriptor)
    }
}
