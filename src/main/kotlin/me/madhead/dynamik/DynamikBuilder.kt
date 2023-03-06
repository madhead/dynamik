package me.madhead.dynamik

import kotlinx.serialization.modules.SerializersModule

/**
 * Builder of the [DynamikConfiguration], used in [`Dynamik { }`][Dynamik] factory function.
 */
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
     * @see DynamikConfiguration.classDiscriminator
     */
    var classDiscriminator: String = dynamik.configuration.classDiscriminator

    /**
     * Module with contextual and polymorphic serializers to be used in the resulting [Dynamik] instance.
     */
    var serializersModule: SerializersModule = dynamik.serializersModule

    internal fun build(): DynamikConfiguration {
        return DynamikConfiguration(
            encodeDefaults = encodeDefaults,
            ignoreUnknownKeys = ignoreUnknownKeys,
            explicitNulls = explicitNulls,
            classDiscriminator = classDiscriminator,
        )
    }
}
