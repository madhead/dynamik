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

    testRuntimeOnly(platform(libs.junit.bom))
    testRuntimeOnly(libs.junit.jupiter.engine)

    jmh(libs.jmh.core)
    jmh(libs.jmh.generator.annprocess)
}

jmh {
    jmhVersion.set(libs.versions.jmh.asProvider().get())
}

tasks {
    test {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
        }
    }
}
