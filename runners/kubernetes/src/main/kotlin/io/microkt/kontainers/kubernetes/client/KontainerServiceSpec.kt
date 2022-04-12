package io.microkt.kontainers.kubernetes.client

import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1Service
import io.kubernetes.client.openapi.models.V1ServiceBuilder
import io.kubernetes.client.openapi.models.V1ServicePortBuilder
import io.microkt.kontainers.domain.KontainerSpec

internal fun createServiceSpec(spec: KontainerSpec, pod: V1Pod, serviceType: String = "NodePort"): V1Service =
    V1ServiceBuilder()
        .withApiVersion("v1")
        .withKind("Service")
        .withNewMetadata()
        .withName(pod.metadata!!.name)
        .withLabels<String, String>(
            mapOf(
                "app" to pod.metadata!!.name,
                "kontainers" to "true"
            )
        )
        .endMetadata()
        .withNewSpec()
        .withType(serviceType)
        .withSelector<String, String>(mapOf("app" to pod.metadata!!.name))
        .withPorts(
            spec.ports.map { kontainerPort ->
                V1ServicePortBuilder()
                    .withName(kontainerPort.port.toString())
                    .withPort(kontainerPort.port)
                    .withNewTargetPort(kontainerPort.port)
                    .build()
            }
        )
        .endSpec()
        .build()
