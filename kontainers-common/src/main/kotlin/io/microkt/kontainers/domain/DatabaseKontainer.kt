package io.microkt.kontainers.domain

interface DatabaseKontainer : Kontainer {
    /**
     * Returns the name of this Kontainer's default database.
     */
    fun getDatabaseName(): String

    /**
     * Returns this Kontainer's default database username.
     */
    fun getUsername(): String

    /**
     * Returns this Kontainer's default database password.
     */
    fun getPassword(): String
}
