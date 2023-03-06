package me.madhead.dynamik.decoder

import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import me.madhead.dynamik.Dynamik

/**
 * Decoder used by [Dynamik] during deserialization.
 * This interface can be used to inject desired behaviour into a deserialization process.
 *
 * Typical example of the usage:
 * ```
 * object SomeSerializer : KSerializer<SomeClass> {
 *     override fun deserialize(decoder: Decoder): SomeClass {
 *         if (decoder is DynamikDecoder) {
 *             // Dynamik-specific logic here
 *             // E.g. one could access the configuration:
 *             val configuration = decoder.dynamik.configuration
 *         } else {
 *             // Currently deserializing the value from some other format
 *         }
 *     }
 * }
 * ```
 */
interface DynamikDecoder : Decoder, CompositeDecoder {
    /**
     * An instance of the current [Dynamik].
     */
    val dynamik: Dynamik
}
