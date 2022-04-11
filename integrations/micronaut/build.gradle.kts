plugins {
    kotlin("jvm")
    kotlin("kapt")
    id("io.micronaut.application") version "3.3.2"
}

val junitVersion: String by project
val kotlinVersion: String by project
val micronautVersion: String = "3.4.1"

dependencies {
    api(project(":kontainers-context"))

    testImplementation(platform("io.micronaut:micronaut-bom:$micronautVersion"))
    kaptTest(platform("io.micronaut:micronaut-bom:$micronautVersion"))

    kaptTest("io.micronaut:micronaut-http-validation")
    kaptTest("io.micronaut.data:micronaut-data-processor")

    testImplementation(project(":kontainers-postgresql"))
    testImplementation(project(":kontainers-redis"))
    testImplementation(project(":kontainers-junit5"))

    testImplementation("io.micronaut:micronaut-http-client")
    testImplementation("io.micronaut:micronaut-jackson-databind")
    testImplementation("io.micronaut:micronaut-runtime")
    testImplementation("io.micronaut.flyway:micronaut-flyway")
    testImplementation("io.micronaut.data:micronaut-data-jdbc")
    testImplementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    testImplementation("io.micronaut.sql:micronaut-jdbc-hikari")
    testImplementation("jakarta.annotation:jakarta.annotation-api")
    testImplementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    testImplementation("io.micronaut:micronaut-validation")
    testImplementation("io.micronaut.test:micronaut-test-junit5")

    testRuntimeOnly("ch.qos.logback:logback-classic")
    testRuntimeOnly("org.postgresql:postgresql")
    testRuntimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("Micronaut Integration")
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

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.example.app.*")
    }
}
