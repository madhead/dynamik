package me.madhead.dynamik.encoder

import me.madhead.dynamik.Dynamik
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

internal class DynamikPrimitiveEncoder(
    dynamik: Dynamik,
    resultConsumer: (Map<String, AttributeValue>) -> Unit
) : AbstractDynamikEncoder(dynamik, resultConsumer) {
    private val PRIMITIVE_TAG: String = "primitive"

    private var content: AttributeValue? = null

    init {
        pushTag(PRIMITIVE_TAG)
    }

    override fun putValue(key: String, value: AttributeValue) {
        require(key === PRIMITIVE_TAG) { "This output can only consume primitives with '$PRIMITIVE_TAG' tag" }
        require(content == null) { "Primitive element was already recorded. Does call to .encodeXxx happen more than once?" }
        content = value
    }

    override fun getCurrent(): Map<String, AttributeValue> {
        return emptyMap()
    }
}
