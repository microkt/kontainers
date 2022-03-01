plugins {
    kotlin("jvm")
}

dependencies {
    api(project(":kontainers-common"))
    api(project(":kontainers-runner-docker"))
    api(project(":kontainers-runner-kubernetes"))
}
