import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

plugins {
    kotlin("multiplatform") apply false
    kotlin("jvm") apply false
}

val javaVersion: String by project

group = "ru.otus.otuskotlin.marketplace"
version = "0.0.3"

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    group = rootProject.group
    version = rootProject.version

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = javaVersion
    }
    tasks.withType<KotlinJvmCompile> {
        kotlinOptions.jvmTarget = javaVersion
    }
}
