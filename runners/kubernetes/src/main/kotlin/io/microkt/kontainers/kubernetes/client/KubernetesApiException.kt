package io.microkt.kontainers.kubernetes.client

import io.kubernetes.client.openapi.ApiException

class KubernetesApiException(
    override val cause: ApiException
) : Exception("K8S error ${cause.responseHeaders} - ${cause.responseBody}", cause)
