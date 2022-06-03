package me.madhead.dynamik

/**
 * Configuration of the current [Dynamik] instance available through [Dynamik.configuration] and configured with [DynamikBuilder] constructor.
 *
 * Standalone configuration object is meaningless and can not be used outside the [Dynamik], neither new [Dynamik] instance can be created from it.
 *
 * Detailed description of each property is available in [DynamikBuilder] class.
 */
data class DynamikConfiguration internal constructor(
    val encodeDefaults: Boolean = false,
    val ignoreUnknownKeys: Boolean = true,
    val explicitNulls: Boolean = false,
    val useArrayPolymorphism: Boolean = false,
    val classDiscriminator: String = "type",
)
