package me.madhead.dynamik

import kotlinx.serialization.InheritableSerialInfo

/**
 * Specifies key for class discriminator value used during polymorphic serialization in [Dynamik].
 *
 * Provided key is used only for an annotated class and its subclasses; to configure global class discriminator,
 * use [DynamikBuilder.classDiscriminator] property.
 *
 * This annotation is [inheritable][InheritableSerialInfo], so it should be sufficient to place it on a base class
 * of the hierarchy. It is not possible to define different class discriminators for different parts of class hierarchy.
 * Pay attention to the fact that class discriminator, same as polymorphic serializer's base class, is
 * determined statically.
 *
 * @see DynamikBuilder.classDiscriminator
 */
@InheritableSerialInfo
@Target(AnnotationTarget.CLASS)
annotation class DynamikClassDiscriminator(val discriminator: String)
