package structuredconcurency

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

suspend fun main() {
    val vocabulary = Vocabulary()

//        findWordsSlowly(vocabulary, "сильный", "любимый")
    findWordsAsync(vocabulary, "привет", "любимый")

}

// Loads sequentially
suspend fun findWordsSlowly(vocabulary: Vocabulary, word: String, word2: String) {
    val result: String
    val result2: String

    val time = measureTimeMillis {
        result = vocabulary.find(word)
        result2 = vocabulary.find(word2, withTime = 5000)
    }

    println("Make some other stuff")
    println("Found $result && $result2")
    println("Working time: $time")
    println("End some other stuff")
}

suspend fun findWordsAsync(vocabulary: Vocabulary, word: String, word2: String) = coroutineScope {
    val start = System.currentTimeMillis()
    val deferred = async { vocabulary.find(word) }
    val deferred2 = async { vocabulary.find(word2, withTime = 5000) }

    println("Make some other stuff")

    println("Found ${deferred.await()} && ${deferred2.await()}")
    println("Working time: ${System.currentTimeMillis() - start}")
    println("End some other stuff")
}


suspend fun findWordsAsyncWithCatch(vocabulary: Vocabulary, word: String, word2: String) = coroutineScope {
    val start = System.currentTimeMillis()
    val deferred = async { vocabulary.find(word) }
    val deferred2 = async { vocabulary.find(word2, withTime = 5000) }

    println("Make some other stuff")

    runCatching {
        println("Found ${deferred.await()} && ${deferred2.await()}")
    }
        .also { println("Working time: ${System.currentTimeMillis() - start}") }
        .onFailure {
            println("Deffered still running? ${deferred.isActive}")
            println("Deffered is canceled? ${deferred.isCancelled}")
            println("Deffered2 still running? ${deferred2.isActive}")
            println("Deffered2 is canceled? ${deferred2.isCancelled}")
        }.getOrThrow()

    println("End some other stuff")
}