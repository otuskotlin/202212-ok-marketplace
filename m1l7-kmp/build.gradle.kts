// TODO-1 (Общие сведения): показать Multiplatform plugin
plugins {
    kotlin("multiplatform")
}

repositories {
    mavenCentral()
}

// TODO-2 (Общие сведения): показать различные target-ы для Multiplatform проекта
kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }

    // TODO (Kotlin/JS): показать возможность выбора компилятора IR
    //  + различные целевые окружения - browser/nodejs
    js(IR) {
        browser {}
    }

    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosArm64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        compilations.getByName("main") {
            cinterops {
                val libcurl by creating
            }
        }
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    val coroutinesVersion: String by project


    // TODO-3: (Общие сведения) Описание модулей, соответствующих нашим целевым платформам
    //  common - общий код, который мы сможем использовать на разных платформах
    //  для каждой целевой платформы можем указать свои специфические зависимости
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutinesVersion")
            }
        }
        val jvmMain by getting
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jsMain by getting {
            dependencies {
                implementation(npm("js-big-decimal", "~1.3.4"))
                implementation(npm("is-sorted", "~1.0.5"))
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val nativeMain by getting
        val nativeTest by getting
    }
}
