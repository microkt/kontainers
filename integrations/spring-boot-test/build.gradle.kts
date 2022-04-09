plugins {
    kotlin("jvm")
}

val junitVersion: String by project
val springBootVersion: String = "2.6.6"

dependencies {
    api(project(":kontainers-context"))

    testImplementation(platform("org.springframework.boot:spring-boot-dependencies:$springBootVersion"))

    testImplementation(project(":kontainers-postgresql"))
    testImplementation(project(":kontainers-redis"))
    testImplementation(project(":kontainers-junit5"))

    testImplementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    testImplementation("org.springframework.boot:spring-boot-starter-webflux")
    testImplementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    testImplementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    testImplementation("org.flywaydb:flyway-core")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    testImplementation("org.springframework:spring-jdbc")
    testImplementation("io.r2dbc:r2dbc-postgresql")
    testRuntimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("io.lettuce:lettuce-core:6.1.6.RELEASE")
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
