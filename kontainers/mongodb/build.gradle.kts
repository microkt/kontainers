plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-factory"))

    testImplementation(project(":kontainers-junit5"))
    testImplementation("org.litote.kmongo:kmongo:4.5.0")
}
