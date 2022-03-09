plugins {
    kotlin("jvm")
}

val junitVersion: String by project

dependencies {
    api(project(":kontainers-runner-factory"))
    implementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation(project(":kontainers-postgresql"))
    testImplementation(project(":kontainers-redis"))
    testImplementation("io.lettuce:lettuce-core:6.1.4.RELEASE")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("Kontainers JUnit 5")
            includes.from("MODULES.md")

            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(
                    uri("https://github.com/MicroKt/kontainers").toURL()
                )
                remoteLineSuffix.set("#L")
            }

            samples.from(
                "$projectDir/src/test/kotlin"
            )

            externalDocumentationLink {
                url.set(uri("https://junit.org/junit5/docs/current/api/").toURL())
            }
        }
    }
}
