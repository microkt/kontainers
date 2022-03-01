package io.microkt.kontainers.docker.client

import com.github.dockerjava.api.command.CreateContainerResponse
import com.github.dockerjava.api.command.InspectContainerResponse
import com.github.dockerjava.api.model.PullResponseItem
import io.microkt.kontainers.domain.KontainerSpec

interface DockerFacade {
    suspend fun create(kontainerSpec: KontainerSpec): CreateContainerResponse
    suspend fun delete(containerId: String)
    suspend fun inspect(containerId: String): InspectContainerResponse
    suspend fun imageExists(image: String): Boolean
    suspend fun pull(image: String): PullResponseItem
    suspend fun start(containerId: String): InspectContainerResponse?
    suspend fun stop(containerId: String)
}
