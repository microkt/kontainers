package io.microkt.kontainers.kubernetes.runner

import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1Service
import io.microkt.kontainers.config.KontainerPropertyDelegate
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.domain.PlatformKontainer
import io.microkt.kontainers.kubernetes.client.CheckedKubernetesApiClient
import io.microkt.kontainers.kubernetes.client.KubernetesClient
import io.microkt.kontainers.kubernetes.client.createServiceSpec
import io.microkt.kontainers.kubernetes.domain.ClusterKubernetesKontainer
import io.microkt.kontainers.kubernetes.domain.ExternalKubernetesKontainer
import io.microkt.kontainers.kubernetes.domain.KubernetesKontainer
import io.microkt.kontainers.runner.KontainerRunner

/**
 * Kubernetes [KontainerRunner] implementation.
 *
 * @author Scott Rossillo
 * @constructor Creates a new [KubernetesKontainerRunner] using the given
 * [kubernetesClient] to communicate with the K8S controller.
 */
class KubernetesKontainerRunner(
    private val kubernetesClient: KubernetesClient = CheckedKubernetesApiClient.create()
) : KontainerRunner {

    private val minikubeIp: String? by KontainerPropertyDelegate.optional

    private fun kubernetesKontainer(spec: KontainerSpec, pod: V1Pod, service: V1Service): KubernetesKontainer =
        if (minikubeIp != null) {
            ExternalKubernetesKontainer(
                kontainerSpec = spec,
                pod = pod,
                service = service,
                kontainerRunner = this,
                clusterIp = minikubeIp!!
            )
        } else {
            ClusterKubernetesKontainer(
                kontainerSpec = spec,
                pod = pod,
                service = service,
                kontainerRunner = this
            )
        }

    override suspend fun create(kontainerSpec: KontainerSpec): PlatformKontainer {
        val pod = kubernetesClient.create(kontainerSpec)
        val service = kubernetesClient.create(createServiceSpec(kontainerSpec, pod))
        return kubernetesKontainer(kontainerSpec, pod, service)
    }

    override suspend fun delete(kontainerId: String) =
        kubernetesClient.delete(kontainerId)

    override suspend fun start(kontainerId: String) {
        // NO-OP: started by create
    }
}
