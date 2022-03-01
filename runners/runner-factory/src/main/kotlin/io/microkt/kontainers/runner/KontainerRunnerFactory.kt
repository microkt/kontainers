package io.microkt.kontainers.runner

import io.microkt.kontainers.config.KontainerPropertyDelegate
import io.microkt.kontainers.docker.runner.DockerKontainerRunner
import io.microkt.kontainers.kubernetes.runner.KubernetesKontainerRunner

class KontainerRunnerFactory {
    enum class Backend {
        DOCKER,
        KUBERNETES
    }

    companion object {
        private fun createKubernetesRunner(): KontainerRunner = KubernetesKontainerRunner()
        private fun createDockerRunner(): KontainerRunner = DockerKontainerRunner()

        fun createRunner(): KontainerRunner =
            when (determineBackend()) {
                Backend.DOCKER -> createDockerRunner()
                Backend.KUBERNETES -> createKubernetesRunner()
            }

        fun determineBackend(): Backend {
            val runnerName: String? by KontainerPropertyDelegate
            return if (!runnerName.isNullOrBlank()) {
                Backend.valueOf(runnerName!!.uppercase())
            } else {
                Backend.DOCKER
            }
        }
    }
}
