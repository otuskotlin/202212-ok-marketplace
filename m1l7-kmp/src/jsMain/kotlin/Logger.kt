actual class Logger {
    // TODO-js-2: использование JS console API из Kotlin через external (native) интерфейс, которые представляет
    //  объекты, реализация которых будет доступна в целевой платформе
    actual fun log(message: String) {
        console.log("JS log: $message")
    }
}