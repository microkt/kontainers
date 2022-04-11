pluginManagement {
    val dokkaVersion: String by settings
    val kotlinVersion: String by settings
    val ktLintVersion: String by settings

    plugins {
        kotlin("jvm") version kotlinVersion
        id("org.jetbrains.dokka") version dokkaVersion
        id("org.jlleitschuh.gradle.ktlint") version ktLintVersion
    }
}

rootProject.name = "kontainers-parent"

// Common
include("kontainers-common")
include("kontainers-context")
include("kontainers-runner-docker")
include("kontainers-runner-kubernetes")
include("kontainers-runner-factory")
include("logging")

// Integrations
include("kontainers-junit5")
include("kontainers-micronaut")
include("kontainers-spring-boot")

// Kontainers
include("kontainers-kafka")
include("kontainers-localstack")
include("kontainers-mariadb")
include("kontainers-mongodb")
include("kontainers-mysql")
include("kontainers-postgresql")
include("kontainers-redis")
include("kontainers-zookeeper")

// Runners
project(":kontainers-runner-docker").projectDir = File(rootProject.projectDir, "./runners/docker")
project(":kontainers-runner-kubernetes").projectDir = File(rootProject.projectDir, "./runners/kubernetes")
project(":kontainers-runner-factory").projectDir = File(rootProject.projectDir, "./runners/runner-factory")

// Kontainers
project(":kontainers-kafka").projectDir = File(rootProject.projectDir, "./kontainers/kafka")
project(":kontainers-localstack").projectDir = File(rootProject.projectDir, "./kontainers/localstack")
project(":kontainers-mariadb").projectDir = File(rootProject.projectDir, "./kontainers/mariadb")
project(":kontainers-mongodb").projectDir = File(rootProject.projectDir, "./kontainers/mongodb")
project(":kontainers-mysql").projectDir = File(rootProject.projectDir, "./kontainers/mysql")
project(":kontainers-postgresql").projectDir = File(rootProject.projectDir, "./kontainers/postgresql")
project(":kontainers-redis").projectDir = File(rootProject.projectDir, "./kontainers/redis")
project(":kontainers-zookeeper").projectDir = File(rootProject.projectDir, "./kontainers/zookeeper")

// Integrations
project(":kontainers-context").projectDir = File(rootProject.projectDir, "./integrations/context")
project(":kontainers-junit5").projectDir = File(rootProject.projectDir, "./integrations/junit-jupiter")
project(":kontainers-micronaut").projectDir = File(rootProject.projectDir, "./integrations/micronaut")
project(":kontainers-spring-boot").projectDir = File(rootProject.projectDir, "./integrations/spring-boot")
