package io.microkt.kontainers.mariadb

import io.microkt.kontainers.domain.MB
import io.microkt.kontainers.dsl.kontainerSpec
import io.microkt.kontainers.mariadb.MariaKontainer.Env.MARIADB_DATABASE
import io.microkt.kontainers.mariadb.MariaKontainer.Env.MARIADB_PASSWORD
import io.microkt.kontainers.mariadb.MariaKontainer.Env.MARIADB_ROOT_PASSWORD
import io.microkt.kontainers.mariadb.MariaKontainer.Env.MARIADB_USER

/**
 * Default MariaDB [KontainerSpec][io.microkt.kontainers.domain.KontainerSpec].
 *
 * @author Scott Rossillo
 * @sample io.microkt.kontainers.mariadb.mariaKontainerSpec
 */
val mariaKontainerSpec = kontainerSpec {
    name = "mariadb"
    image = "mariadb:10.7"
    environment {
        set(MARIADB_DATABASE to "test")
        set(MARIADB_ROOT_PASSWORD to "test")
        set(MARIADB_USER to "test")
        set(MARIADB_PASSWORD to "test")
    }
    ports {
        expose tcp 3306
    }
    resources {
        limit memory 128.MB
    }
}
