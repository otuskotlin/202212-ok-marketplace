package continuation

import kotlinx.coroutines.delay

/**
 * How to decompile file in IDEA
 *
 * 1. Select class: build -> classes -> kotlin -> main -> continuation -> DecompileMePleaseKt.class
 * 2. Decompile: Tools -> Kotlin -> Decompile to Java
 */

suspend fun myFunction() {
    println("Before")
    var counter = 0

    delay(1000) // suspending

    counter++
    println("After: $counter")
}