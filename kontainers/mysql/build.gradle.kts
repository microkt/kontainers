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

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("MySQL Kontainer")
            includes.from("MODULES.md")

            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(
                    uri("https://github.com/MicroKt/kontainers").toURL()
                )
                remoteLineSuffix.set("#L")
            }

            samples.from(
                "$projectDir/src/main/kotlin",
                "$projectDir/src/test/kotlin"
            )
        }
    }
}
