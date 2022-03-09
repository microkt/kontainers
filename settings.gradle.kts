pluginManagement {
    val kotlinVersion: String by settings
    val ktLintVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jetbrains.dokka") version kotlinVersion
        id("org.jlleitschuh.gradle.ktlint") version ktLintVersion
    }
}

rootProject.name = "kontainers-parent"

include("kontainers-common")
include("kontainers-runner-docker")
include("kontainers-runner-kubernetes")
include("kontainers-runner-factory")
include("logging")

include("kontainers-junit5")

include("kontainers-kafka")
include("kontainers-localstack")
include("kontainers-mongodb")
include("kontainers-mysql")
include("kontainers-postgresql")
include("kontainers-redis")
include("kontainers-zookeeper")

project(":kontainers-runner-docker").projectDir = File(rootProject.projectDir, "./runners/docker")
project(":kontainers-runner-kubernetes").projectDir = File(rootProject.projectDir, "./runners/kubernetes")
project(":kontainers-runner-factory").projectDir = File(rootProject.projectDir, "./runners/runner-factory")

project(":kontainers-kafka").projectDir = File(rootProject.projectDir, "./kontainers/kafka")
project(":kontainers-localstack").projectDir = File(rootProject.projectDir, "./kontainers/localstack")
project(":kontainers-mongodb").projectDir = File(rootProject.projectDir, "./kontainers/mongodb")
project(":kontainers-mysql").projectDir = File(rootProject.projectDir, "./kontainers/mysql")
project(":kontainers-postgresql").projectDir = File(rootProject.projectDir, "./kontainers/postgresql")
project(":kontainers-redis").projectDir = File(rootProject.projectDir, "./kontainers/redis")
project(":kontainers-zookeeper").projectDir = File(rootProject.projectDir, "./kontainers/zookeeper")
