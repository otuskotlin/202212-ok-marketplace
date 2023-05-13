import org.jetbrains.kotlin.gradle.internal.ensureParentDirsCreated

plugins {
    kotlin("jvm")
}

val generatedPath = "$buildDir/generated/main/kotlin"
sourceSets {
    main {
        java.srcDir(generatedPath)
    }
}

dependencies {
    val arcadeDbVersion: String by project
    val tinkerpopVersion: String by project
    val coroutinesVersion: String by project
    val kmpUUIDVersion: String by project
    val testContainersVersion: String by project

    implementation(project(":ok-marketplace-common"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.benasher44:uuid:$kmpUUIDVersion")

    testImplementation(project(":ok-marketplace-repo-tests"))

    implementation(kotlin("stdlib-jdk8"))
    implementation("org.apache.tinkerpop:gremlin-driver:$tinkerpopVersion")
    implementation("com.arcadedb:arcadedb-engine:$arcadeDbVersion")
    implementation("com.arcadedb:arcadedb-network:$arcadeDbVersion")
    implementation("com.arcadedb:arcadedb-gremlin:$arcadeDbVersion")

    testImplementation(kotlin("test-junit"))
    testImplementation("org.testcontainers:testcontainers:$testContainersVersion")
}

val arcadeDbVersion: String by project

tasks {
    val gradleConstants by creating {
        file("$generatedPath/GradleConstants.kt").apply {
            ensureParentDirsCreated()
            writeText(
                """
                    package ru.otus.otuskotlin.marketplace.backend.repository.gremlin
                    
                    const val ARCADEDB_VERSION = "$arcadeDbVersion"
                """.trimIndent()
            )
        }
    }
    compileKotlin.get().dependsOn(gradleConstants)
}
