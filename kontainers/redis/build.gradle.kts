plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))

    testImplementation(project(":kontainers-junit5"))
    // Redis Driver
    testImplementation("io.lettuce:lettuce-core:6.1.6.RELEASE")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("Redis Kontainer")
            includes.from("MODULES.md")
        }
    }
}
