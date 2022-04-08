package io.microkt.kontainers.junit5.extension

import io.microkt.kontainers.domain.Kontainer
import org.junit.jupiter.api.extension.ParameterContext
import kotlin.reflect.full.isSubclassOf

internal fun ParameterContext.kotlinType() = this.parameter!!.type!!.kotlin
internal fun ParameterContext.isKontainerType() = this.kotlinType().isSubclassOf(Kontainer::class)
