plugins {
    kotlin("jvm")
}

val kotlinxVersion: String by project

dependencies {
    api(project(":kontainers-common"))

    api("com.github.docker-java:docker-java-core:3.2.11")
    api("com.github.docker-java:docker-java-transport-zerodep:3.2.11")

    api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$kotlinxVersion")
}
