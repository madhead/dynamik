package me.madhead.dynamik.types.polymorphic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class Project {
    abstract val name: String
    var status = "open"
}

@Serializable
@SerialName("owned")
class OwnedProject(override val name: String, val owner: String) : Project()
