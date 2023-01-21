package structuredconcurency

import kotlinx.coroutines.delay

class Vocabulary {
    private val words = listOf("сильный", "могущественный", "невозмутимый", "любимый")

    suspend fun find(word: String, withTime: Long = 2000): String {
        println("Start searching $word")
        delay(withTime)
        return words.find { it.matches(Regex(pattern = "${word}.*$")) }
            ?.also { println("Done searching $word") }
            ?: throw RuntimeException("Word $word not found in dict")
    }
}