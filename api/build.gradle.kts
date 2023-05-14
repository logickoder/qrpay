@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.graalvm)
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.dependency)
    `java-library`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(project(":model"))

    // JWT
    implementation(libs.jwt.api)
    runtimeOnly(libs.jwt.impl)
    runtimeOnly(libs.jwt.jackson)

    // Kotlin
    implementation(libs.kotlin.coroutines.reactor)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.serialization)

    // Postgres
    implementation(libs.postgre)

    // Project Reactor
    implementation(libs.projectreactor)
    testImplementation(libs.projectreactor.test)

    // Spring
    implementation(libs.spring.acutator)
    implementation(libs.spring.data)
    implementation(libs.spring.jackson)
    implementation(libs.spring.security)
    implementation(libs.spring.validation)
    implementation(libs.spring.webflux)
    developmentOnly(libs.spring.devtools)
    testImplementation(libs.spring.test)

    testImplementation(libs.junit)
    testImplementation(libs.junit.engine)
    testImplementation(libs.mockk)
}

tasks.withType<Test> {
    useJUnitPlatform()
}