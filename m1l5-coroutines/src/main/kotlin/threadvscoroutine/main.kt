package threadvscoroutine

import kotlinx.coroutines.runBlocking
import threadvscoroutine.runCoroutine
import threadvscoroutine.runThread

fun main() {
//    runThread()
    runBlocking {
        runCoroutine()
    }
}