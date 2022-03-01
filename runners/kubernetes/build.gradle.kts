plugins {
    kotlin("jvm")
}

val kotlinxVersion: String by project
val kubernetesClientVersion = "13.0.0"

dependencies {
    api(project(":kontainers-common"))

    api("io.kubernetes:client-java:$kubernetesClientVersion")
    api("io.kubernetes:client-java-api-fluent:$kubernetesClientVersion")

    api("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$kotlinxVersion")
}
