package me.madhead.dynamik

import kotlinx.serialization.SerializationException
import kotlinx.serialization.modules.SerializersModule

class DynamikBuilder internal constructor(dynamik: Dynamik) {
    /**
     * Specifies whether default values of Kotlin properties should be encoded.
     * `false` by default.
     */
    var encodeDefaults: Boolean = dynamik.configuration.encodeDefaults

    /**
     * Specifies whether encounters of unknown properties in the input maps should be ignored instead of throwing [SerializationException].
     * `true` by default.
     */
    var ignoreUnknownKeys: Boolean = dynamik.configuration.ignoreUnknownKeys

    /**
     * Specifies whether `null` values should be encoded for nullable properties.
     *
     * When this flag is disabled properties with `null` values without default are not encoded;
     * during decoding, the absence of a field value is treated as `null` for nullable properties without a default value.
     *
     * `false` by default.
     */
    var explicitNulls: Boolean = dynamik.configuration.explicitNulls

    /**
     * Module with contextual and polymorphic serializers to be used in the resulting [Dynamik] instance.
     */
    var serializersModule: SerializersModule = dynamik.serializersModule

    internal fun build(): DynamikConfiguration {
        return DynamikConfiguration(
            encodeDefaults,
            ignoreUnknownKeys,
            explicitNulls
        )
    }
}
