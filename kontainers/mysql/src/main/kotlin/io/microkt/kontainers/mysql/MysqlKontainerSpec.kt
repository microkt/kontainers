package io.microkt.kontainers.mysql

import io.microkt.kontainers.domain.MB
import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.mysql.MysqlKontainer.Env.MYSQL_DATABASE
import io.microkt.kontainers.mysql.MysqlKontainer.Env.MYSQL_PASSWORD
import io.microkt.kontainers.mysql.MysqlKontainer.Env.MYSQL_ROOT_PASSWORD
import io.microkt.kontainers.mysql.MysqlKontainer.Env.MYSQL_USER

/**
 * Default MySQL [KontainerSpec][io.microkt.kontainers.domain.KontainerSpec].
 *
 * @author Scott Rossillo
 * @sample io.microkt.kontainers.mysql.mysqlKontainerSpec
 */
val mysqlKontainerSpec = kontainerSpec {
    name = "mysql"
    image = "mysql:8.0-oracle"
    environment {
        set(MYSQL_DATABASE to "test")
        set(MYSQL_ROOT_PASSWORD to "test")
        set(MYSQL_USER to "test")
        set(MYSQL_PASSWORD to "test")
    }
    ports {
        expose tcp 3306
    }
    resources {
        limit memory 256.MB
    }
}
