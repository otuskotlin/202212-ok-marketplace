package continuation

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.suspendCoroutine

fun main() = runBlocking {
    println("Start")

//    launch {
//        delay(1000)
//        repeat(5) {
//            delay(500)
//            println("Working....")
//        }
//        println("Done")
//    }
//
//    suspendCoroutine<Unit> { continuation ->
////        continuation.resume(Unit)
//    }

    println("Finish")
}