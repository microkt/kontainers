# Module Kontainers Common

Fix me soon

# Package io.microkt.kontainers.domain

Kontainers domain specification.

# Package io.microkt.kontainers.dsl

Provides a Kotlin domain specific language for building Kontainer specifications.

```kotlin
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
```
