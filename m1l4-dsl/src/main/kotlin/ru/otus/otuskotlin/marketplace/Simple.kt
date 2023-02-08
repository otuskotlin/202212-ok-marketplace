package ru.otus.otuskotlin.marketplace

fun sout(block: () -> Any?) {
    val result = block()
    println(result)
}

class MyContext {
    fun time() = System.currentTimeMillis()
//    // Same as:
//    fun time(): Long {
//        return System.currentTimeMillis()
//    }
}

fun soutWithTimestamp(block: MyContext.() -> Any?) {
    val context = MyContext()
    val result = block(context)
    println(result)
}

infix fun String.time(value: String): String {
    return "$this:$value"
}
