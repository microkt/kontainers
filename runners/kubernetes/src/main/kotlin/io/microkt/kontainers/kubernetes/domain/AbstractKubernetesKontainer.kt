package io.microkt.kontainers.kubernetes.domain

import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1Service
import io.microkt.kontainers.domain.KontainerSpec
import io.microkt.kontainers.runner.KontainerRunner

/**
 * Provides an abstract [KubernetesKontainer] for methods common to all
 * Kubernetes hosted [Kontainers][io.microkt.kontainers.domain.Kontainer].
 *
 * @author Scott Rossillo
 */
abstract class AbstractKubernetesKontainer(
    override val kontainerSpec: KontainerSpec,
    private val pod: V1Pod,
    protected open val service: V1Service,
    private val kontainerRunner: KontainerRunner
) : KubernetesKontainer {
    final override val id: String
        get() = pod.metadata!!.uid!!

    final override suspend fun start(timeout: Long) =
        kontainerRunner.start(kontainerSpec.name)

    final override suspend fun remove() =
        kontainerRunner.delete(pod.metadata!!.name!!)
}
