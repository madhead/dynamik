package me.madhead.dynamik.encoder

import kotlinx.serialization.descriptors.SerialDescriptor
import me.madhead.dynamik.Dynamik
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

internal class DynamikListEncoder(
    dynamik: Dynamik,
    resultConsumer: (Map<String, AttributeValue>) -> Unit
) : AbstractDynamikEncoder(dynamik, resultConsumer) {
    private val array: ArrayList<AttributeValue> = arrayListOf()

    override fun elementName(descriptor: SerialDescriptor, index: Int): String = index.toString()

    override fun putValue(key: String, value: AttributeValue) {
        val idx = key.toInt()
        array.add(idx, value)
    }

    override fun getCurrent(): Map<String, AttributeValue> {
        return emptyMap()
    }
}
