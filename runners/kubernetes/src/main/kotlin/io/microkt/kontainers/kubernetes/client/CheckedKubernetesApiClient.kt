package io.microkt.kontainers.kubernetes.client

import io.kubernetes.client.openapi.ApiClient
import io.kubernetes.client.openapi.ApiException
import io.kubernetes.client.openapi.Configuration
import io.kubernetes.client.openapi.apis.CoreV1Api
import io.kubernetes.client.openapi.models.V1Pod
import io.kubernetes.client.openapi.models.V1Service
import io.kubernetes.client.util.Config
import io.microkt.kontainers.domain.KontainerSpec

class CheckedKubernetesApiClient(api: CoreV1Api, namespace: String) : KubernetesApiClient(api, namespace) {
    override fun create(kontainerSpec: KontainerSpec): V1Pod =
        try {
            super.create(kontainerSpec)
        } catch (e: ApiException) {
            throw KubernetesApiException(e)
        }

    override fun create(serviceSpec: V1Service): V1Service =
        try {
            super.create(serviceSpec)
        } catch (e: ApiException) {
            throw KubernetesApiException(e)
        }

    companion object {
        fun create(): KubernetesClient {
            val namespace: String by io.microkt.kontainers.config.KontainerPropertyDelegate.required
            val client: ApiClient = Config.defaultClient()
            Configuration.setDefaultApiClient(client)
            return CheckedKubernetesApiClient(CoreV1Api(), namespace)
        }
    }
}
