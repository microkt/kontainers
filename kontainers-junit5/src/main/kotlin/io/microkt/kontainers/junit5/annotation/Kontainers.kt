package io.microkt.kontainers.junit5.annotation

import io.microkt.kontainers.junit5.extension.KontainerExtension
import org.junit.jupiter.api.extension.ExtendWith

@Target(AnnotationTarget.CLASS)
@ExtendWith(KontainerExtension::class)
annotation class Kontainers
