import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("org.jlleitschuh.gradle.ktlint")
}

allprojects {
    apply {
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    group = "io.mircokt.kontainers"
    version = "1.0.0-alpha.1"
    repositories {
        mavenCentral()
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        reporters {
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.PLAIN_GROUP_BY_FILE)
            reporter(org.jlleitschuh.gradle.ktlint.reporter.ReporterType.CHECKSTYLE)
        }
    }
}

subprojects {
    apply {
        plugin("java")
        plugin("maven-publish")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("org.jetbrains.dokka")
    }

    val kotlinVersion: String by project
    val kotlinLoggingVersion: String by project
    val logbackVersion: String by project
    val junitVersion: String by project
    val mockkVersion: String by project

    java.sourceCompatibility = JavaVersion.VERSION_11

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
        implementation("io.github.microutils:kotlin-logging-jvm:$kotlinLoggingVersion")

        testImplementation(platform("org.junit:junit-bom:$junitVersion"))
        testRuntimeOnly(platform("org.junit:junit-bom:$junitVersion"))

        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("io.mockk:mockk:$mockkVersion")
        testRuntimeOnly("ch.qos.logback:logback-classic:$logbackVersion")
        testRuntimeOnly(project(":logging"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xopt-in=kotlin.RequiresOptIn")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            excludeTags("docker", "kubernetes")
        }
        testLogging {
            showStandardStreams = true
        }
    }

    tasks.register<Test>("dockerIntegrationTest") {
        useJUnitPlatform {
            includeTags("docker")
        }
        environment["KONTAINERS_RUNNER"] = "docker"
    }

    tasks.register<Test>("kubernetesIntegrationTest") {
        useJUnitPlatform {
            includeTags("kubernetes")
        }
        environment["KONTAINERS_RUNNER"] = "kubernetes"
    }

    publishing {
        publications {
            create<MavenPublication>("main") {
                from(components["java"])
            }
        }
        repositories {
            maven {
                name = "packagecloud"
                url = uri("https://packagecloud.io/microkt/kontainers/java/maven2")
                credentials {
                    username = System.getenv("PACKAGECLOUD_TOKEN") ?: ""
                    password = ""
                }
            }
        }
    }
}

tasks.dokkaHtmlMultiModule.configure {
    moduleName.set("Kontainers")
    moduleVersion.set(project.version.toString())
    includes.from("MODULES.md")
}
