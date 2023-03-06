package me.madhead.dynamik

import kotlinx.serialization.SerializationException

/**
 * Configuration of the current [Dynamik] instance available through [Dynamik.configuration]
 * and configured with [DynamikBuilder] constructor.
 *
 * Standalone configuration object is meaningless and can nor be used outside the
 * [Dynamik], neither new [Dynamik] instance can be created from it.
 */
data class DynamikConfiguration internal constructor(
    /**
     * Specifies whether default values of Kotlin properties should be encoded.
     * `false` by default.
     */
    val encodeDefaults: Boolean = false,

    /**
     * Specifies whether encounters of unknown properties in the input JSON
     * should be ignored instead of throwing [SerializationException].
     * `false` by default.
     */
    val ignoreUnknownKeys: Boolean = true,

    /**
     * Specifies whether `null` values should be encoded for nullable properties.
     *
     * When this flag is disabled properties with `null` values without default are not encoded;
     * during decoding, the absence of a field value is treated as `null` for nullable properties without a default value.
     *
     * `false` by default.
     */
    val explicitNulls: Boolean = false,

    /**
     * Name of the class descriptor property for polymorphic serialization.
     *
     * "type" by default.
     */
    val classDiscriminator: String = "type",
)
