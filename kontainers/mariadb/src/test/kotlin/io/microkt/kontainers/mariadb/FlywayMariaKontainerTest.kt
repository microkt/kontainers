package io.microkt.kontainers.mariadb

import io.microkt.kontainers.junit5.annotation.Kontainers
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Tags
import org.junit.jupiter.api.Test
import java.sql.Statement
import javax.sql.DataSource

@Kontainers
@Tags(
    Tag("docker"),
    Tag("kubernetes")
)
internal class FlywayMariaKontainerTest(mariaKontainer: MariaKontainer) {
    private var dataSource: DataSource = buildHikariDataSource(mariaKontainer)

    init {
        val flyway = Flyway.configure().dataSource(dataSource).load()
        flyway.migrate()
    }

    @Test
    fun testQueryAnimalsTable() {
        val statement: Statement = dataSource.connection.createStatement()
        statement.execute("SELECT count(*) from animals")

        val resultSet = statement.resultSet
        resultSet.next()

        val resultInt = resultSet.getInt(1)
        assertEquals(6, resultInt, "select count should return 6")
    }
}
