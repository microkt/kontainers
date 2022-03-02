package io.microkt.kontainers.mysql

import io.microkt.kontainers.dsl.kontainerSpec

private const val FSYNC_OFF_OPTION = "fsync=off"

val mysqlKontainerSpec = kontainerSpec {
    name = "mysql"
    image = "${this.name}:${MysqlDefaults.VERSION}"
    environment {
        set("MYSQL_DATABASE" to MysqlDefaults.DATABASE_NAME)
        set("MYSQL_ROOT_PASSWORD" to MysqlDefaults.USERNAME)
        set("MYSQL_USER" to MysqlDefaults.USERNAME)
        set("MYSQL_PASSWORD" to MysqlDefaults.PASSWORD)
    }
    ports {
        expose tcp MysqlDefaults.PORT
    }
}
