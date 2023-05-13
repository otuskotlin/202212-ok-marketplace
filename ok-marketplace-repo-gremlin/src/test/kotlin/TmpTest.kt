package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.apache.tinkerpop.gremlin.driver.AuthProperties
import org.apache.tinkerpop.gremlin.driver.Cluster
import org.apache.tinkerpop.gremlin.driver.remote.DriverRemoteConnection
import org.apache.tinkerpop.gremlin.process.traversal.AnonymousTraversalSource.traversal
import org.apache.tinkerpop.gremlin.structure.Vertex
import org.junit.Ignore
import org.junit.Test
import ru.otus.otuskotlin.marketplace.common.models.MkplAd
import ru.otus.otuskotlin.marketplace.common.models.MkplUserId
import ru.otus.otuskotlin.marketplace.common.repo.DbAdIdRequest
import ru.otus.otuskotlin.marketplace.common.repo.DbAdRequest
import kotlin.test.fail
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.`__` as bs


@Ignore("Тест для экспериментов")
@OptIn(ExperimentalCoroutinesApi::class)
class TmpTest {

    @Test
    fun z() {
//        val db = RemoteDatabase("localhost", 2480, "mkpl", "root", "root_root").use {
//            val z = db.command("gremlin", "g.V()")
//            println("ZZL: ${z.stream().map { it.toJSON() }.asSequence().joinToString("\n")}")
//        }

        val authProp = AuthProperties().apply {
            with(AuthProperties.Property.USERNAME, "root")
            with(AuthProperties.Property.PASSWORD, "root_root")
        }
        val cluster = Cluster.build()
            .addContactPoints("localhost")
            .port(8182)
//            .credentials("root", "playwithdata")
            .authProperties(authProp)
            .create()
        // Должно работать таким образом, но на текущий момент не работает из-за бага в ArcadeDb
        // Тест в ArcadeDb задизейблен:
        // https://github.com/ArcadeData/arcadedb/blob/main/gremlin/src/test/java/com/arcadedb/server/gremlin/ConnectRemoteGremlinServer.java
        traversal()
            .withRemote(DriverRemoteConnection.using(cluster, "g"))
            .use { g ->
                val userId = g
                    .addV("User")
                    .property("name", "Evan")
                    .next()
                    .id()
                println("UserID: $userId")
            }
    }

    @Test
    @Ignore
    fun y() {
        val host = "localhost"
        val port = 8182
        val cluster = Cluster.build().apply {
            addContactPoints(host)
            port(port)
//            credentials("root", "root@root")
            credentials("gremlin", "gremlin%gremlin")
//            path("/mkpl")
//            enableSsl(enableSsl)
        }.create()
        val g = traversal()
            .withRemote(DriverRemoteConnection.using(cluster))
        val x = g.V().hasLabel("Test").`as`("a")
            .project<Any?>("lock", "ownerId", "z")
            .by("lock")
            .by(bs.inE("Owns").outV().id())
            .by(bs.elementMap<Vertex, Map<Any?, Any?>>())
            .toList()

        println("CONTENT: ${x}")
        g.close()
    }

    @Test
    @Ignore
    fun x() {
//        val host = ArcadeDbContainer.container.host
//        val port = ArcadeDbContainer.container.getMappedPort(8182)
        val host = "localhost"
        val port = 8182
        val cluster = Cluster.build().apply {
            addContactPoints(host)
            port(port)
//            credentials("root", "root@root")
            credentials("gremlin", "gremlin%gremlin")
//            path("/mkpl")
//            enableSsl(enableSsl)
        }.create()
        val g = traversal()
            .withRemote(DriverRemoteConnection.using(cluster))
        val userId = g
            .addV("User")
            .property("name", "Ivan")
            .next()
            .id()
        println("UserID: $userId")

        val id = g
            .addV("Test")
            .`as`("a")
            .property("lock", "111")
            .addE("Owns")
            .from(bs.V<Vertex>(userId))
            .select<Vertex>("a")
            .next()
            .id()
        println("ID: $id")

        val owner = g
            .V(userId)
            .outE("Owns")
            .where(bs.inV().id().`is`(id))
            .toList()
        println("OWNER: $owner")

//        val n = g
//            .V(id)
//            .`as`("a")
//            .choose(
//                bs.select<Vertex, Any>("a")
//                    .values<String>("lock")
//                    .`is`("1112"),
//                bs.select<Vertex, String>("a").drop().inject("success"),
//                bs.constant("lock-failure")
//            ).toList()
//        println("YYY: $n")

//        val x = g.V(id).`as`("a")
//            .union(
//                bs.select<Vertex, Edge>("a")
//                    .inE("User")
//                    .outV()
//                    .V(),
//                bs.select<Vertex, Vertex>("a")
//            )
//            .elementMap<Any>()
//            .toList()
//        println("CONTENT: ${x}")
        g.close()
    }

    @Test
    @Ignore
    fun repoTest() = runTest {
        val initObj = MkplAd(
            title = "x",
            description = "y",
            ownerId = MkplUserId("123"),
//            adType = MkplDealSide.SUPPLY,
//            visibility = MkplVisibility.VISIBLE_PUBLIC,
        )
        val repo = AdRepoGremlin(hosts = "localhost")
        val resAd = repo.createAd(DbAdRequest(ad = initObj))
        val ad = resAd.data ?: fail("Empty object")
        val adRead = repo.readAd(DbAdIdRequest(ad.id))
        println("adRead: $adRead")

        val adDel = repo.deleteAd(DbAdIdRequest(ad.id, ad.lock))
        println("adDel: $adDel")
        val adDeleted = repo.readAd(DbAdIdRequest(ad.id))
        println("adRead: $adDeleted")
    }
}
