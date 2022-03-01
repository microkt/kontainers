package io.microkt.kontainers.kubernetes.domain

import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1Service
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.KontainerRunner

/**
 * Provides a Kubernetes parent container for kontainers launched
 * from within a Kubernetes cluster.
 */
class ClusterKubernetesKontainer(
    kontainerSpec: KontainerSpec,
    pod: V1Pod,
    service: V1Service,
    kontainerRunner: KontainerRunner
) : Kontainer, AbstractKubernetesKontainer(
    kontainerSpec, pod, service, kontainerRunner
) {
    override fun getPort(containerPort: Int): Int = containerPort
    override fun getPort(): Int = kontainerSpec.ports.first().port
    override fun getAddress(): String = service.spec!!.clusterIP!!
    override fun getDirectAddress(): String = this.getAddress()
}
