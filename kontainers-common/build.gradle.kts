plugins {
    kotlin("jvm")
}

val kotlinxVersion: String by project

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:$kotlinxVersion")
}
