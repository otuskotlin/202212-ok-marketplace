# Otus Kotlin Marketplace 2022-12

Учебный проект курса [Otus](https://otus.ru) [Kotlin Backend Developer](https://otus.ru/lessons/kotlin/).
Поток курса 2022-12.

Marketplace -- это площадка, на которой пользователи выставляют предложения и потребности. Задача
площадки -- предоставить наиболее подходящие варианты в обоих случаях: для предложения -- набор вариантов с
потребностями, для потребностей -- набор вариантов с предложениями.

## Документация

1. Маркетинг
    1. [Заинтерессанты](./docs/01-marketing/01-stakeholders.md)
    2. [Целевая аудитория](./docs/01-marketing/02-target-audience.md)
    3. [Пользовательские истории](./docs/01-marketing/03-user-stories.md)
    4. [Бизнес-требования](./docs/01-marketing/04-brd.md)
2. Тесты
3. Архитектура

## Модули проекта
1. [m1l1-quickstart](m1l1-quickstart) - Быстрый старт, первая программа и тест
2. [m1l3-oop](m1l3-oop) - Объектно-ориентированное программирование
3. [m1l4-dsl](m1l4-dsl) - Предметно ориентированные языки (DSL)
4. [m1l5-coroutines](m1l5-coroutines) - Асинхронное и многопоточное программирование с корутинами

[//]: # (4. [m1l6-flows-and-channels]&#40;m1l6-flows-and-channels&#41; - Асинхронное и многопоточное программирование с Flow и каналами)

[//]: # (5. [m1l7-kmp]&#40;m1l7-kmp&#41; - Kotlin Multiplatform и интероперабельность с JVM, JS)

[//]: # (6. [m2l2-testing]&#40;m2l2-testing&#41; - Тестирование проекта, TDD, MDD)

[//]: # ()

[//]: # (## Транспортные модели, API)

[//]: # ()

[//]: # (1. [specs]&#40;specs&#41; - описание API в форме OpenAPI-спецификаций)

[//]: # (2. [ok-marketplace-api-v1-jackson]&#40;ok-marketplace-api-v1-jackson&#41; - Генерация первой версии транспортных модеелй с)

[//]: # (   Jackson)

[//]: # (3. [ok-marketplace-api-v2-kmp]&#40;ok-marketplace-api-v2-kmp&#41; - Генерация второй версии транспортных модеелй с KMP)

[//]: # (4. [ok-marketplace-common]&#40;ok-marketplace-common&#41; - модуль с общими классами для модулей проекта. В частности, там)

[//]: # (   располагаются внутренние модели и контекст.)

[//]: # (5. [ok-marketplace-mappers-v1]&#40;ok-marketplace-mappers-v1&#41; - Мапер между внутренними моделями и моделями API v1)

[//]: # (6. [ok-marketplace-mappers-v2]&#40;ok-marketplace-mappers-v2&#41; - Мапер между внутренними моделями и моделями API v1)

[//]: # ()

[//]: # (## Фреймворки и транспорты)

[//]: # ()

[//]: # (1. [ok-marketplace-app-spring]&#40;ok-marketplace-app-spring&#41; - Приложение на Spring Framework)

[//]: # (1. [ok-marketplace-app-ktor]&#40;ok-marketplace-app-ktor&#41; - Приложение на Ktor JVM/Native)

[//]: # (1. [ok-marketplace-app-knative]&#40;ok-marketplace-app-serverless&#41; - Приложение для Yandex.Cloud lambda)

[//]: # (1. [ok-marketplace-app-rabbit]&#40;ok-marketplace-app-rabbit&#41; - Микросервис на RabbitMQ)

[//]: # (1. [ok-marketplace-app-kafka]&#40;ok-marketplace-app-kafka&#41; - Микросервис на Kafka)

[//]: # ()

[//]: # (## Модули бизнес-логики)

[//]: # ()

[//]: # (1. [ok-marketplace-stubs]&#40;ok-marketplace-stubs&#41; - Стабы для ответов сервиса)

[//]: # (1. [ok-marketplace-biz]&#40;ok-marketplace-biz&#41; - Модуль бизнес-логики приложения)

[//]: # ()

[//]: # (## Хранение, репозитории, базы данных)

[//]: # ()

[//]: # (1. [ok-marketplace-repo-tests]&#40;ok-marketplace-repo-tests&#41; - Базовые тесты для репозиториев всех баз данных)

[//]: # (2. [ok-marketplace-repo-inmemory]&#40;ok-marketplace-repo-inmemory&#41; - Репозиторий на базе кэша в памяти для тестирования)

[//]: # (3. [ok-marketplace-repo-postgresql]&#40;ok-marketplace-repo-postgresql&#41; - Репозиторий на базе PostgreSQL)

[//]: # (4. [ok-marketplace-repo-cassandra]&#40;ok-marketplace-repo-cassandra&#41; - Репозиторий на базе Cassandra)

[//]: # (5. [ok-marketplace-repo-gremlin]&#40;ok-marketplace-repo-gremlin&#41; - Репозиторий на базе Apache TinkerPop Gremlin и ArcadeDb)

[//]: # (### Функции &#40;эндпониты&#41;)

[//]: # ()

[//]: # (1. CRUDS &#40;create, read, update, delete, search&#41; для объявлений &#40;ad&#41;)

[//]: # (1. ad.offers &#40;опционально&#41;)

[//]: # ()

[//]: # (### Описание сущности ad)

[//]: # ()

[//]: # (1. Info)

[//]: # (    1. Title)

[//]: # (    2. Description)

[//]: # (    3. Owner)

[//]: # (    4. Visibility)

[//]: # (2. DealSide: Demand/Proposal)

[//]: # (3. ProductType &#40;гаечный ключ, ...&#41;)

[//]: # (4. ProductId - идентификатор модели товара)
