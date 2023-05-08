package ru.otus.otuskotlin.marketplace.backend.repository.gremlin

import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import java.time.Duration

object ArcadeDbContainer {
    val username: String = "root"
    val password: String = "root_root"
    val container by lazy {
        GenericContainer(DockerImageName.parse("arcadedata/arcadedb:${ARCADEDB_VERSION}")).apply {
            withExposedPorts(2480, 2424, 8182)
            withEnv(
                "JAVA_OPTS", "-Darcadedb.server.rootPassword=$password " +
                        "-Darcadedb.server.plugins=GremlinServer:com.arcadedb.server.gremlin.GremlinServerPlugin"
            )
//            // Строчки ниже почему-то не работают больше
//            withEnv("arcadedb.server.rootPassword", "1r2d3g4h@5j6k7l8p")
//            withEnv("arcadedb.server.plugins", "GremlinServer:com.arcadedb.server.gremlin.GremlinServerPlugin")
//            withEnv("arcadedb.server.defaultDatabases", "OpenBeer[root]{import:https://github.com/ArcadeData/arcadedb-datasets/raw/main/orientdb/OpenBeer.gz}")
            waitingFor(Wait.forLogMessage(".*ArcadeDB Server started.*\\n", 1))
            withStartupTimeout(Duration.ofMinutes(5))
            start()
            println("ARCADE: http://${host}:${getMappedPort(2480)}")
            println("ARCADE: http://${host}:${getMappedPort(2424)}")
//            Thread.sleep(5000)
            println(this.logs)
            println("RUNNING?: ${this.isRunning}")
        }
    }
}
