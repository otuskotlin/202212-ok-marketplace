plugins {
    kotlin("jvm")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val coroutinesVersion: String by project
val jacksonVersion: String by project

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion") // from string to object

    implementation("com.squareup.okhttp3:okhttp:4.9.3") // http client

    testImplementation(kotlin("test-junit"))
}
