package io.microkt.kontainers.runner

import io.microkt.kontainers.config.KontainerPropertyDelegate
import io.microkt.kontainers.docker.runner.DockerKontainerRunner
import io.microkt.kontainers.kubernetes.runner.KubernetesKontainerRunner

/**
 * Factory capable of providing a [KontainerRunner] for the appropriate [Backend].
 *
 * @author Scott Rossillo
 */
class KontainerRunnerFactory {
    /**
     * Enumeration of supported [KontainerRunner] backend platforms.
     */
    enum class Backend {
        DOCKER,
        KUBERNETES
    }

    companion object {
        private fun createKubernetesRunner(): KontainerRunner = KubernetesKontainerRunner()
        private fun createDockerRunner(): KontainerRunner = DockerKontainerRunner()

        /**
         * Creates a [KontainerRunner] after determining the backend platform to use.
         *
         * @see determineBackend
         */
        fun createRunner(): KontainerRunner =
            when (determineBackend()) {
                Backend.DOCKER -> createDockerRunner()
                Backend.KUBERNETES -> createKubernetesRunner()
            }

        /**
         * Returns the [Backend] to use for running [Kontainers][io.microkt.kontainers.domain.Kontainer].
         * Defaults to [Backend.DOCKER] if [KontainerPropertyDelegate] hasn't discovered a Kubernetes
         * configuration.
         */
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
