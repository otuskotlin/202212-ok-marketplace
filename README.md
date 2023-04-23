# Otus Kotlin Marketplace 2022-12

Учебный проект курса [Otus](https://otus.ru) [Kotlin Backend Developer](https://otus.ru/lessons/kotlin/).
Поток курса 2022-12.

Marketplace -- это площадка, на которой пользователи выставляют предложения и потребности. Задача
площадки -- предоставить наиболее подходящие варианты в обоих случаях: для предложения -- набор вариантов с
потребностями, для потребностей -- набор вариантов с предложениями.

## Визуальная схема фронтенда

![Макет фронта](imgs/design-layout.png)

## Документация

1. Маркетинг
    1. [Заинтересанты](./docs/01-marketing/01-stakeholders.md)
    2. [Целевая аудитория](./docs/01-marketing/02-target-audience.md)
    3. [Конкурентный анализ](./docs/01-marketing/03-concurrency.md)
    4. [Анализ экономики](./docs/01-marketing/04-economy.md)
    5. [Пользовательские истории](./docs/01-marketing/05-user-stories.md)
2. DevOps
    1. [Схема инфраструктуры](./docs/02-devops/01-infrastruture.md)
    2. [Схема мониторинга](./docs/02-devops/02-monitoring.md)
3. Тесты
4. Архитектура
    1. [Компонентная схема](./docs/04-architecture/01-arch.md)
    2. [Интеграционная схема](./docs/04-architecture/02-integration.md)
    3. [Описание API](./docs/04-architecture/03-api.md)

# Структура проекта

## Подпроекты для занятий по языку Kotlin

1. [m1l1-quickstart](m1l1-quickstart) - Быстрый старт, первая программа и тест
2. [m1l3-oop](m1l3-oop) - Объектно-ориентированное программирование
3. [m1l4-dsl](m1l4-dsl) - Предметно ориентированные языки (DSL)
4. [m1l5-coroutines](m1l5-coroutines) - Асинхронное и многопоточное программирование с корутинами
5. [m1l6-flows-and-channels](m1l6-flows-and-channels) - Асинхронное и многопоточное программирование с Flow и каналами
6. [m1l7-kmp](m1l7-kmp) - Kotlin Multiplatform и интероперабельность с JVM, JS
7. [m2l3-testing](m2l3-testing) - Тестирование проекта, TDD, MDD

## Транспортные модели, API

1. [specs](specs) - описание API в форме OpenAPI-спецификаций
2. [ok-marketplace-api-v1-jackson](ok-marketplace-api-v1-jackson) - Генерация первой версии транспортных модеелй с
   Jackson
3. [ok-marketplace-api-v2-kmp](ok-marketplace-api-v2-kmp) - Генерация второй версии транспортных моделей с KMP
4. [ok-marketplace-api-log1](ok-marketplace-api-log1) - Генерация первой версии моделей логирования
5. [ok-marketplace-common](ok-marketplace-common) - модуль с общими классами для модулей проекта. В частности, там
   располагаются внутренние модели и контекст.
6. [ok-marketplace-mappers-v1](ok-marketplace-mappers-v1) - Мапер между внутренними моделями и моделями API v1
7. [ok-marketplace-mappers-v2](ok-marketplace-mappers-v2) - Мапер между внутренними моделями и моделями API v2
8. [ok-marketplace-mappers-log1](ok-marketplace-mappers-log1) - Мапер между внутренними моделями и моделями логирования
   первой версии

## Фреймворки и транспорты

1. [ok-marketplace-app-spring](ok-marketplace-app-spring) - Приложение на Spring Framework
2. [ok-marketplace-app-ktor](ok-marketplace-app-ktor) - Приложение на Ktor JVM/Native
3. [ok-marketplace-app-serverless](ok-marketplace-app-serverless) - Приложение для Yandex.Cloud lambda
4. [ok-marketplace-app-rabbit](ok-marketplace-app-rabbit) - Микросервис на RabbitMQ
5. [ok-marketplace-app-kafka](ok-marketplace-app-kafka) - Микросервис на Kafka

## Мониторинг и логирование

1. [deploy](deploy) - Инструменты мониторинга и деплоя
2. [ok-marketplace-lib-logging-common](ok-marketplace-lib-logging-common) - Общие объявления для логирования
3. [ok-marketplace-lib-logging-kermit](ok-marketplace-lib-logging-kermit) - Библиотека логирования на базе библиотеки
   Kermit
4. [ok-marketplace-lib-logging-logback](ok-marketplace-lib-logging-logback) - Библиотека логирования на базе библиотеки
   Logback

## Модули бизнес-логики

1. [ok-marketplace-stubs](ok-marketplace-stubs) - Стабы для ответов сервиса
2. [ok-marketplace-lib-cor](ok-marketplace-lib-cor) - Библиотека цепочки обязанностей для бизнес-логики
3. [ok-marketplace-biz](ok-marketplace-biz) - Модуль бизнес-логики приложения
4. [ok-marketplace-lib-konform](ok-marketplace-lib-konform) - Применение библиотеки валидации Konform

# (## Хранение, репозитории, базы данных)

1. [ok-marketplace-repo-tests](ok-marketplace-repo-tests) - Базовые тесты для репозиториев всех баз данных
2. [ok-marketplace-repo-in-memory](ok-marketplace-repo-in-memory) - Репозиторий на базе кэша в памяти для тестирования
[//]: # (3. [ok-marketplace-repo-postgresql]&#40;ok-marketplace-repo-postgresql&#41; - Репозиторий на базе PostgreSQL)

[//]: # (4. [ok-marketplace-repo-cassandra]&#40;ok-marketplace-repo-cassandra&#41; - Репозиторий на базе Cassandra)

[//]: # (5. [ok-marketplace-repo-gremlin]&#40;ok-marketplace-repo-gremlin&#41; - Репозиторий на базе Apache TinkerPop Gremlin и ArcadeDb)
