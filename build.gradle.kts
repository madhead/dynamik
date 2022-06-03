import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.jmh)
}

repositories {
    mavenCentral()
}

dependencies {
    api(platform(libs.kotlin.bom))
    api(platform(libs.kotlinx.serialization.bom))
    api(libs.kotlinx.serialization.core)
    api(libs.dynamodb)

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter.api)
    testImplementation(libs.junit.jupiter.params)
    testImplementation(libs.dynamodb.enhanced)
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-json")
    testImplementation("org.jetbrains.kotlinx:kotlinx-serialization-properties")

    testRuntimeOnly(platform(libs.junit.bom))
    testRuntimeOnly(libs.junit.jupiter.engine)

    jmh(libs.jmh.core)
    jmh(libs.jmh.generator.annprocess)
}

jmh {
    jmhVersion.set(libs.versions.jmh.asProvider().get())
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=kotlinx.serialization.ExperimentalSerializationApi",
                "-opt-in=kotlinx.serialization.InternalSerializationApi",
            )
        }
    }
    test {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
        }
    }
}
