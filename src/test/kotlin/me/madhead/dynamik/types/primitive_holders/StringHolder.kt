package me.madhead.dynamik.types.primitive_holders

import kotlinx.serialization.Serializable

@Serializable
data class StringHolder(
    val value: String,
    val inner: StringHolder? = null
)
