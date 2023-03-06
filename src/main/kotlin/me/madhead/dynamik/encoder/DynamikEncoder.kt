package me.madhead.dynamik.encoder

import kotlinx.serialization.encoding.CompositeEncoder
import kotlinx.serialization.encoding.Encoder
import me.madhead.dynamik.Dynamik

/**
 * Encoder used by [Dynamik] during serialization.
 * This interface can be used to inject desired behaviour into a serialization process.
 *
 * Typical example of the usage:
 * ```
 * object SomeSerializer : KSerializer<SomeClass> {
 *     override fun serialize(encoder: Encoder, value: SomeClass) {
 *         if (encoder is DynamikEncoder) {
 *             // Dynamik-specific logic here
 *             // E.g. one could access the configuration:
 *             val configuration = encoder.dynamik.configuration
 *         } else {
 *             // Currently serializing the value in some other format
 *         }
 *     }
 * }
 * ```
 */
interface DynamikEncoder : Encoder, CompositeEncoder {
    /**
     * An instance of the current [Dynamik].
     */
    val dynamik: Dynamik
}
