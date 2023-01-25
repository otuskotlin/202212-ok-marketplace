actual class Logger {
    // TODO (Kotlin/JS): использование JS console API из Kotlin через external (native) интерфейс
    actual fun log(message: String) {
        console.log("JS log: $message")
    }
}