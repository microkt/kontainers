plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))

    // Postgres JDBC Driver & Hikari Connection Pool
    testImplementation("org.postgresql:postgresql:42.2.23")
    testImplementation("com.zaxxer:HikariCP:4.0.3")
}
