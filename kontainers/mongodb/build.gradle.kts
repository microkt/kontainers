plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))

    testImplementation(project(":kontainers-junit5"))
    testImplementation("org.litote.kmongo:kmongo:4.5.0")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("MongoDB Kontainer")
            includes.from("MODULES.md")
            samples.from(
                "$projectDir/src/main/kotlin",
                "$projectDir/src/test/kotlin"
            )
        }
    }
}
