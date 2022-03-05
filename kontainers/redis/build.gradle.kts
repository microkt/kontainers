plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))

    // Redis Driver
    testImplementation("io.lettuce:lettuce-core:6.1.6.RELEASE")
}
