package io.microkt.kontainers.postgresql

import io.microkt.kontainers.docker.runner.DockerKontainerRunner
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.sql.Statement
import javax.sql.DataSource

@Tag("docker")
internal class PostgresDockerKontainerTest {
    companion object {

        lateinit var postgres: PostgresKontainer
        lateinit var dataSource: DataSource

        @BeforeAll
        @JvmStatic
        fun setUp() {
            postgres = PostgresKontainerFactory().createKontainer(postgresKontainerSpec, DockerKontainerRunner())
            postgres.startSync()
            dataSource = buildDataSource(postgres)
        }

        @AfterAll
        @JvmStatic
        fun tearDown() {
            postgres.removeSync()
        }
    }

    @Test
    fun testQuery() {
        val statement: Statement = dataSource.connection.createStatement()
        statement.execute("SELECT 1")

        val resultSet = statement.resultSet
        resultSet.next()

        val resultInt = resultSet.getInt(1)
        assertEquals(1, resultInt, "SELECT 1; should return 1")
    }

    @Test
    fun testValidPort() {
        Assertions.assertNotNull(postgres.getPort(postgresKontainerSpec.ports.first().port))
    }
}
