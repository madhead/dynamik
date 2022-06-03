package me.madhead.dynamik

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InheritableSerialInfo

@InheritableSerialInfo
@Target(AnnotationTarget.CLASS)
@ExperimentalSerializationApi
annotation class DynamikClassDiscriminator(val discriminator: String)
