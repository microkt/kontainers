package io.microkt.kontainers.kubernetes.domain

import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1Service
import io.microkt.kontainers.domain.Kontainer
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.KontainerRunner

/**
 * Provides a Kubernetes parent container for kontainers launched
 * from outside a Kubernetes cluster, such as onto minikube.
 */
class ExternalKubernetesKontainer(
    kontainerSpec: KontainerSpec,
    pod: V1Pod,
    service: V1Service,
    kontainerRunner: KontainerRunner,
    private val clusterIp: String
) : Kontainer, AbstractKubernetesKontainer(
    kontainerSpec, pod, service, kontainerRunner
) {
    override fun getPort(containerPort: Int): Int? =
        service.spec!!.ports!!.first { it.port == containerPort }.nodePort

    override fun getPort(): Int? =
        service.spec!!.ports!!.first().nodePort

    override fun getAddress(): String = clusterIp

    override fun getDirectAddress(): String = service.spec!!.clusterIP!!
}
