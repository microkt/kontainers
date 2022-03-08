plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))

    testImplementation(project(":kontainers-junit5"))
    testImplementation(project(":logging"))
    testImplementation("mysql:mysql-connector-java:8.0.28")
    testImplementation("com.zaxxer:HikariCP:4.0.3")
    testImplementation("org.flywaydb:flyway-mysql:8.5.1")
}
