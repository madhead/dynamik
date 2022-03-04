package me.madhead.dynamik.types

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey
import java.time.Instant

@DynamoDbBean
data class Customer(
    @get:DynamoDbPartitionKey
    var id: Long? = null,

    var name: String? = null,

    var registrationDate: Instant? = null,
)
