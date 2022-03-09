# Module Kontainers Common

Defines the Kontainers domain, DSL, and specifications for platforms capable
of running Kontainer instances.

# Package io.microkt.kontainers.config

Provides configuration necessary to bootstrap a Kontainer runner.

# Package io.microkt.kontainers.domain

Kontainers domain specification.

# Package io.microkt.kontainers.dsl

Provides a Kotlin domain specific language for building Kontainer specifications.

```kotlin
val myKontainerSpec = kontainerSpec {
    name = "foo"
    image = "foo:version"
    environment {
        set("FOO" to "bar")
    }
    ports {
        expose tcp 8080
    }
}
```

# Package io.microkt.kontainers.runner

Provides an interface to be implemented by any platform capable of running
Kontainers.
