package io.microkt.kontainers.mysql

import io.microkt.kontainers.junit5.annotation.Kontainers
import org.junit.jupiter.api.Assertions
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
internal class MysqlKontainerTest(private val mysql: MysqlKontainer) {
    private var dataSource: DataSource = buildHikariDataSource(mysql)

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
        Assertions.assertNotNull(mysql.getPort(mysqlKontainerSpec.ports.first().port))
    }
}
