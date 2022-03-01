plugins {
    kotlin("jvm")
}

val junitVersion: String by project

dependencies {
    api(project(":kontainers-runner-factory"))
    implementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    testImplementation(project(":kontainers-postgresql"))
    testImplementation(project(":kontainers-redis"))
    testImplementation("io.lettuce:lettuce-core:6.1.4.RELEASE")
}
