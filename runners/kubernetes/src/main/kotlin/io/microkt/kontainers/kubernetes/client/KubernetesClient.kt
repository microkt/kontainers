package io.microkt.kontainers.kubernetes.client

import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1Service
import io.microkt.kontainers.domain.KontainerSpec

interface KubernetesClient {
    fun create(kontainerSpec: KontainerSpec): V1Pod
    fun create(serviceSpec: V1Service): V1Service
    fun delete(name: String)
}
