package io.microkt.kontainers.kubernetes.client

import io.kubernetes.client.custom.Quantity
import io.kubernetes.client.openapi.models.V1ContainerPort
import io.kubernetes.client.openapi.models.V1EnvVar
import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1PodBuilder
import io.kubernetes.client.openapi.models.V1ResourceRequirements
import io.kubernetes.client.openapi.models.V1SecurityContext
import io.kubernetes.client.openapi.models.V1Service
import io.kubernetes.client.openapi.models.V1ServiceBuilder
import io.kubernetes.client.openapi.models.V1ServicePortBuilder
import io.microkt.kontainers.domain.KontainerSpec

// FIXME: need to make memory and CPU configurable
fun createPodSpec(spec: KontainerSpec, uniqueName: String): V1Pod =
    V1PodBuilder()
        .withNewMetadata()
        .withName(uniqueName)
        .withLabels<String, String>(
            mapOf(
                "app" to uniqueName,
                "group" to spec.name,
                "kontainers" to "true"
            )
        )
        .endMetadata()
        .withNewSpec()
        .addNewContainer()
        .withName(spec.name)
        .withImage(spec.image)
        .withImagePullPolicy("Always")
        .withArgs(spec.command)
        .withEnv(spec.environment.map { (k, v) -> V1EnvVar().name(k).value(v) })
        .withPorts(spec.ports.map { V1ContainerPort().containerPort(it.port) })
        .withSecurityContext(V1SecurityContext().allowPrivilegeEscalation(false))
        .withResources(
            V1ResourceRequirements()
                .limits(
                    mapOf(
                        "cpu" to Quantity.fromString("0.4"),
                        "memory" to Quantity.fromString(spec.resources.memory.toString())
                    )
                )
                .requests(
                    mapOf(
                        "cpu" to Quantity.fromString("0.4"),
                        "memory" to Quantity.fromString(spec.resources.memory.toString())
                    )
                )
        )
        .endContainer()
        .endSpec()
        .build()

fun createServiceSpec(spec: KontainerSpec, pod: V1Pod): V1Service =
    V1ServiceBuilder()
        .withApiVersion("v1")
        .withKind("Service")
        .withNewMetadata()
        .withName(pod.metadata!!.name)
        .withLabels<String, String>(mapOf("app" to pod.metadata!!.name, "kontainers" to "true"))
        .endMetadata()
        .withNewSpec()
        .withType("NodePort")
        .withSelector<String, String>(mapOf("app" to pod.metadata!!.name))
        .withPorts(
            V1ServicePortBuilder()
                .withName(spec.ports.first().port.toString())
                .withPort(spec.ports.first().port)
                .withNewTargetPort(spec.ports.first().port)
                .build()
        )
        .endSpec()
        .build()
