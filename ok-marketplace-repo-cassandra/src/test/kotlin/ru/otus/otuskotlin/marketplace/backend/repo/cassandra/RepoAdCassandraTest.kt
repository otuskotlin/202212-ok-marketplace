package ru.otus.otuskotlin.marketplace.backend.repo.cassandra

import org.testcontainers.containers.CassandraContainer
import ru.otus.otuskotlin.marketplace.backend.repo.tests.*
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplAdLock
import ru.otus.otuskotlin.marketplace.common.repo.IAdRepository
import java.time.Duration

class RepoAdCassandraCreateTest : RepoAdCreateTest() {
    override val repo: IAdRepository = TestCompanion.repository(initObjects, "ks_create", lockNew)
}

class RepoAdCassandraDeleteTest : RepoAdDeleteTest() {
    override val repo: IAdRepository = TestCompanion.repository(initObjects, "ks_delete", lockOld)
}

class RepoAdCassandraReadTest : RepoAdReadTest() {
    override val repo: IAdRepository = TestCompanion.repository(initObjects, "ks_read", MkplAdLock(""))
}

class RepoAdCassandraSearchTest : RepoAdSearchTest() {
    override val repo: IAdRepository = TestCompanion.repository(initObjects, "ks_search", MkplAdLock(""))
}

class RepoAdCassandraUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = TestCompanion.repository(initObjects, "ks_update", lockNew)
}

class TestCasandraContainer : CassandraContainer<TestCasandraContainer>("cassandra:3.11.2")

object TestCompanion {
    private val container by lazy {
        TestCasandraContainer().withStartupTimeout(Duration.ofSeconds(300L))
            .also { it.start() }
    }

    fun repository(initObjects: List<MkplAd>, keyspace: String, lock: MkplAdLock): RepoAdCassandra {
        return RepoAdCassandra(
            keyspaceName = keyspace,
            host = container.host,
            port = container.getMappedPort(CassandraContainer.CQL_PORT),
            testing = true, randomUuid = { lock.asString() }, initObjects = initObjects
        )
    }
}
