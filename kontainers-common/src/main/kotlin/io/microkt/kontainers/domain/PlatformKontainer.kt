package io.microkt.kontainers.domain

/**
 * Provides an interface to be implemented by [Kontainer] delegates
 * capable of translating method invocations into vendor platform calls.
 *
 * @author Scott Rossillo
 * @see io.microkt.kontainers.docker.domain.DockerKontainer
 * @see io.microkt.kontainers.kubernetes.domain.KubernetesKontainer
 */
interface PlatformKontainer : Kontainer
