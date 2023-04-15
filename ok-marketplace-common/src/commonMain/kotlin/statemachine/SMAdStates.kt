package ru.otus.otuskotlin.marketplace.common.statemachine


@Suppress("unused")
enum class SMAdStates {
    NONE, // не инициализировано состояние
    DRAFT,    // черновик
    NEW,      // только что опубликовано
    ACTUAL,   // актуальное
    OLD,      // старое объявление
    HIT,      // новое объявление с большим числом просмотров
    POPULAR,  // актуальное объявление с большим числом просмотров

    ERROR,    // ошибки вычисления;

}
