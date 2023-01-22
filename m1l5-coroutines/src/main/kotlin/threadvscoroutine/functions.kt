package threadvscoroutine

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

fun runThread() {
    val threads = List(100_000) {
        thread {
            Thread.sleep(2000L)
            print(".")
        }
    }

    threads.forEach { it.join() }
}

suspend fun runCoroutine() = coroutineScope {
    List(100_000) {
        launch {
            delay(2000L)
            print(".")
        }
    }
}