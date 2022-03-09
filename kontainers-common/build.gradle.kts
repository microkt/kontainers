plugins {
    kotlin("jvm")
}

val kotlinxVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$kotlinxVersion")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        named("main") {
            moduleName.set("Kontainers Common")
            includes.from("MODULES.md")
        }
    }
}
