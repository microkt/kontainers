package io.microkt.kontainers.kubernetes.client

import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1Service
import io.microkt.kontainers.domain.KontainerSpec
import mu.KotlinLogging

abstract class KubernetesApiClient(
    private val api: CoreV1Api,
    private val namespace: String
) : KubernetesClient {
    private val log = KotlinLogging.logger { }

    override fun create(kontainerSpec: KontainerSpec): V1Pod {
        val podSpec = createPodSpec(kontainerSpec, "${kontainerSpec.name}-${randomString()}")
        val pod = api.createNamespacedPod(namespace, podSpec, null, null, null)
        log.info { "Launched pod $pod" }

        return pod
    }

    override fun create(serviceSpec: V1Service): V1Service {
        val service = api.createNamespacedService(namespace, serviceSpec, null, null, null)
        log.info { "Launched service $service" }
        return service
    }

    override fun delete(name: String) {
        api.deleteNamespacedService(name, namespace, null, null, 0, null, null, null)
        api.deleteNamespacedPod(name, namespace, null, null, 0, null, null, null)
    }
}
