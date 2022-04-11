plugins {
    kotlin("jvm")
}

val junitVersion: String by project

dependencies {
    api(project(":kontainers-runner-factory"))
    testImplementation(project(":kontainers-postgresql"))
    testImplementation(project(":kontainers-redis"))
    testImplementation("io.lettuce:lettuce-core:6.1.8.RELEASE")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("Kontainers Integration Context")
            includes.from("MODULES.md")

            samples.from(
                "$projectDir/src/test/kotlin"
            )

            externalDocumentationLink {
                url.set(uri("https://junit.org/junit5/docs/current/api/").toURL())
            }
        }
    }
}
