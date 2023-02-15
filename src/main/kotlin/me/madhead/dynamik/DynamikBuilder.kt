package me.madhead.dynamik

import kotlinx.serialization.modules.SerializersModule

class DynamikBuilder internal constructor(dynamik: Dynamik) {
    /**
     * @see DynamikConfiguration.encodeDefaults
     */
    var encodeDefaults: Boolean = dynamik.configuration.encodeDefaults

    /**
     * @see DynamikConfiguration.ignoreUnknownKeys
     */
    var ignoreUnknownKeys: Boolean = dynamik.configuration.ignoreUnknownKeys

    /**
     * @see DynamikConfiguration.explicitNulls
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
