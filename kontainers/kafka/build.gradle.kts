plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))
    implementation(project(":kontainers-zookeeper"))

    testImplementation(project(":kontainers-junit5"))
    testImplementation("org.apache.kafka:kafka-clients:2.8.1")
}
tasks.withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
    dokkaSourceSets {
        configureEach {
            suppress.set(true)
        }
    }
}
