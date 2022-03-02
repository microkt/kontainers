package io.microkt.kontainers.mysql

import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.mysql.MysqlKontainer.Env.MYSQL_DATABASE
import io.microkt.kontainers.mysql.MysqlKontainer.Env.MYSQL_PASSWORD
import io.microkt.kontainers.mysql.MysqlKontainer.Env.MYSQL_ROOT_PASSWORD
import io.microkt.kontainers.mysql.MysqlKontainer.Env.MYSQL_USER

val mysqlKontainerSpec = kontainerSpec {
    name = MysqlKontainer.Default.IMAGE
    image = "${this.name}:${MysqlKontainer.Default.VERSION}"
    environment {
        set(MYSQL_DATABASE to MysqlKontainer.Default.DATABASE_NAME)
        set(MYSQL_ROOT_PASSWORD to MysqlKontainer.Default.USERNAME)
        set(MYSQL_USER to MysqlKontainer.Default.USERNAME)
        set(MYSQL_PASSWORD to MysqlKontainer.Default.PASSWORD)
    }
    ports {
        expose tcp MysqlKontainer.Default.PORT
    }
}
