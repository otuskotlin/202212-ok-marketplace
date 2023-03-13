terraform {
  required_providers {
    yandex = {
      source = "yandex-cloud/yandex"
    }
  }
  required_version = ">= 0.13"
}

locals {
  bucket   = "ok-marketplace-terraform"
  version  = "1"
  jar_name = "marketplace-serverless-${local.version}.jar"

  // Secrets
  YC_TOKEN     = ""
  YC_CLOUD_ID  = ""
  YC_FOLDER_ID = ""
}


provider "yandex" {
  token     = local.YC_TOKEN
  folder_id = local.YC_FOLDER_ID
  zone      = "ru-central1-a"
}

resource "yandex_iam_service_account" "sa" {
  name = "terraform-sa"
}

# Создание статического ключа доступа
resource "yandex_iam_service_account_static_access_key" "sa-static-key" {
  service_account_id = yandex_iam_service_account.sa.id
  description        = "static access key for object storage"
}

# Назначаем роль сервисному аккаунту
resource "yandex_resourcemanager_folder_iam_member" "sa-editor" {
  folder_id = local.YC_FOLDER_ID
  role      = "storage.editor"
  member    = "serviceAccount:${yandex_iam_service_account.sa.id}"
}

# Создание бакета с использованием ключа
resource "yandex_storage_bucket" "jar-bucket" {
  access_key = yandex_iam_service_account_static_access_key.sa-static-key.access_key
  secret_key = yandex_iam_service_account_static_access_key.sa-static-key.secret_key
  bucket     = local.bucket
}

# Загрузка jar в бакет
resource "yandex_storage_object" "marketplace-serverless-jar" {
  access_key = yandex_iam_service_account_static_access_key.sa-static-key.access_key
  secret_key = yandex_iam_service_account_static_access_key.sa-static-key.secret_key
  bucket     = local.bucket
  key        = local.jar_name
  source     = "../../build/libs/ok-marketplace-app-serverless-0.0.1-SNAPSHOT-all.jar"
}


# Создаем gateway для проксирования запросов в функцию
resource "yandex_api_gateway" "test-api-gateway" {
  name        = "ok-marketplace-gateway"
  description = "Serverless marketplace gateway"
  spec = <<-EOT
    openapi: "3.0.0"
    info:
      version: 1.0.0
      title: Test API
    paths:
      /{proxy+}:
        post:
          summary: all
          operationId: anyId
          parameters:
            - name: proxy
              in: path
              description: path
              required: true
              schema:
                type: string
          x-yc-apigateway-integration:
            type: cloud_functions
            function_id: ${yandex_function.marketplace-serverless.id}
            tag: "$latest"
            service_account_id: ${yandex_iam_service_account.sa.id}
  EOT
}

# Создание функции
resource "yandex_function" "marketplace-serverless" {
  name              = "marketplace-serverless"
  description       = "marketplace serverless"
  user_hash         = "marketplace-serverless-${local.version}"
  runtime           = "java11"
  entrypoint        = "ru.otus.otuskotlin.marketplace.serverlessapp.api.RoutingHandler"
  memory            = "128"
  execution_timeout = "10"
  package {
    bucket_name = local.bucket
    object_name = local.jar_name
  }
}

# Разрешаем вызывать функцию сервисному аккаунту
resource "yandex_function_iam_binding" "function-iam" {
  function_id = yandex_function.marketplace-serverless.id
  role        = "serverless.functions.invoker"
  members = [
    "serviceAccount:${yandex_iam_service_account.sa.id}",
  ]
}
