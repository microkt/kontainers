plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))

    testImplementation(project(":kontainers-junit5"))
    testImplementation(project(":logging"))
    testImplementation("org.mariadb.jdbc:mariadb-java-client:3.0.3")
    testImplementation("com.zaxxer:HikariCP:4.0.3")
    testImplementation("org.flywaydb:flyway-mysql:8.5.1")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("MySQL Kontainer")
            includes.from("MODULES.md")
            samples.from(
                "$projectDir/src/main/kotlin",
                "$projectDir/src/test/kotlin"
            )
        }
    }
}
