package io.microkt.kontainers.kubernetes.client

import io.kubernetes.client.custom.Quantity
import io.kubernetes.client.openapi.models.V1ContainerPort
import io.kubernetes.client.openapi.models.V1EnvVar
import io.kubernetes.client.openapi.models.V1EnvVarBuilder
import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1PodBuilder
import io.kubernetes.client.openapi.models.V1ResourceRequirements
import io.kubernetes.client.openapi.models.V1SecurityContext
import io.microkt.kontainers.domain.KontainerSpec
import java.math.BigDecimal

private val podIpVar: V1EnvVar =
    V1EnvVarBuilder()
        .withName("MY_POD_IP")
        .withNewValueFrom()
        .withNewFieldRef()
        .withFieldPath("status.podIP")
        .endFieldRef()
        .endValueFrom()
        .build()

private val podName: V1EnvVar =
    V1EnvVarBuilder()
        .withName("MY_POD_NAME")
        .withNewValueFrom()
        .withNewFieldRef()
        .withFieldPath("metadata.name")
        .endFieldRef()
        .endValueFrom()
        .build()

// FIXME: need to make memory and CPU configurable
internal fun createPodSpec(spec: KontainerSpec, uniqueName: String): V1Pod =
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
        .withEnv(
            mutableListOf<V1EnvVar>()
                .apply {
                    addAll(spec.environment.map { (k, v) -> V1EnvVar().name(k).value(v) })
                    add(podIpVar)
                    add(podName)
                }
        )
        .withPorts(spec.ports.map { V1ContainerPort().containerPort(it.port) })
        .withSecurityContext(V1SecurityContext().allowPrivilegeEscalation(false))
        .withResources(
            V1ResourceRequirements()
                .limits(
                    mapOf(
                        "cpu" to Quantity.fromString("0.99"),
                        "memory" to Quantity(BigDecimal.valueOf(spec.resources.memory.toLong()), Quantity.Format.DECIMAL_SI)
                    )
                )
                .requests(
                    mapOf(
                        "cpu" to Quantity.fromString("0.99"),
                        "memory" to Quantity(BigDecimal.valueOf(spec.resources.memory.toLong()), Quantity.Format.DECIMAL_SI)
                    )
                )
        )
        .endContainer()
        .endSpec()
        .build()
