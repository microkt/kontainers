plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))

    testImplementation(project(":kontainers-junit5"))

    // Postgres JDBC Driver & Hikari Connection Pool
    testImplementation("org.postgresql:postgresql:42.2.23")
    testImplementation("com.zaxxer:HikariCP:4.0.3")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("PostgreSQL Kontainer")
            includes.from("MODULES.md")
            samples.from(
                "$projectDir/src/main/kotlin",
                "$projectDir/src/test/kotlin"
            )
        }
    }
}
