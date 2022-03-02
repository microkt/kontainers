plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))

    testImplementation(project(":kontainers-junit5"))
    testImplementation("mysql:mysql-connector-java:8.0.28")
    testImplementation("com.zaxxer:HikariCP:4.0.3")
}
