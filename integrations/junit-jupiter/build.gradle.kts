plugins {
    kotlin("jvm")
}

val junitVersion: String by project

dependencies {
    api(project(":kontainers-context"))
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

            samples.from(
                "$projectDir/src/test/kotlin",
                "${project.rootProject.projectDir}/integrations/spring-boot/src/test/kotlin",
                "${project.rootProject.projectDir}/integrations/spring-boot/src/test/resources"
            )

            externalDocumentationLink {
                url.set(uri("https://junit.org/junit5/docs/current/api/").toURL())
            }
        }
    }
}
