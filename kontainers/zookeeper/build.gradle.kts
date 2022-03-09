plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))

    testImplementation(project(":kontainers-junit5"))
    testImplementation("io.ktor:ktor-network:1.6.7")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("Zookeeper Kontainer")
            includes.from("MODULES.md")
            samples.from(
                "$projectDir/src/main/kotlin",
                "$projectDir/src/test/kotlin"
            )
        }
    }
}
