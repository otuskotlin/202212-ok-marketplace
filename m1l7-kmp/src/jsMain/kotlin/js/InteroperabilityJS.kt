package js

import org.w3c.dom.Window
// TODO-js-4: обёртка над Window JS API
external val window: Window

val windowLocation = window.location

// TODO-js-5: вызов JS-кода через функцию js, неудобно для сопровождения
fun useMathRound(value: Double) = js("Math.round(value)")

// TODO-js-4: использование аннотаций для JS модулей
@JsModule("is-sorted")
@JsNonModule
external fun <T> sorted(array: Array<T>): Boolean


// TODO-js-5: использование оберток для JS модулей.
//  Можно сгененить из TS, используя dukat (jsGenerateExternals).
@JsModule("js-big-decimal")
@JsNonModule
@JsName("bigDecimal")
external class JsBigDecimal(value: Any) {
    fun getValue(): String
    fun getPrettyValue(digits: Int, separator: String)
    fun round(precision: Int = definedExternally, mode: dynamic = definedExternally): JsBigDecimal
    companion object {
        fun getPrettyValue(number: Any, digits: Int, separator: String)
    }
}