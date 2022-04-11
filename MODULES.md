# Kontainers

Kontainers is a [Kotlin] framework providing the capability to run [Open Container Images][oci] (OCI) on
Docker, Kubernetes, and possibly other platforms in the near future. This can be valuable in cases where you want to run a
database or messaging system for integration testing and then tear down such
resources after tests complete.

Kontainers enables users to write and run integration tests locally with
[Docker Desktop] or [Minikube], while also utilizing modern, Kubernetes based CI/CD
systems that may not permit or support the Docker runtime.

## Architecture

Kontainers provides a platform-agnostic Kontainer specification that allows users
to define a request to run a container using a simple, [domain specific language][kontainers-dsl]
(DSL). The specification is then used by a Kontainer factory to start the container
on either Docker or Kubernetes, depending on the capabilities of the platform on
which your code is run.

## Getting Started

To start working with Kontainers, read the [Kontainers Getting Started Guide][getting-started].

[getting-started]: https://microkt.io/docs/kontainers/guides/getting-started
[kontainers-dsl]: https://fixme.example.com
[Kotlin]: https://kotlinlang.org/
[kotlin-server-side]: https://kotlinlang.org/lp/server-side/
[docker desktop]: https://www.docker.com/products/docker-desktop
[minikube]: https://minikube.sigs.k8s.io/docs/start/
[oci]: https://opencontainers.org/
