package me.madhead.dynamik.encoder

import me.madhead.dynamik.Dynamik
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

//internal class DynamikMapEncoder(
//    dynamik: Dynamik,
//    resultConsumer: (Map<String, AttributeValue>) -> Unit
//) : DynamikTreeEncoder(dynamik, resultConsumer) {
//    private lateinit var tag: String
//    private var isKey = true
//
//    override fun putValue(key: String, value: AttributeValue) {
////        if (isKey) { // writing key
////            tag = when (value) {
////                is JsonPrimitive -> element.content
////                is JsonObject -> throw InvalidKeyKindException(JsonObjectSerializer.descriptor)
////                is JsonArray -> throw InvalidKeyKindException(JsonArraySerializer.descriptor)
////            }
////            isKey = false
////        } else {
//        content[tag] = value
//        isKey = true
////        }
//    }
//
//    override fun getCurrent(): Map<String, AttributeValue> = content
//}
