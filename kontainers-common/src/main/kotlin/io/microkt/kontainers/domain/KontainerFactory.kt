package io.microkt.kontainers.domain

import kotlin.reflect.KClass

interface KontainerFactory<T : Kontainer> {
    val kontainerSpec: KontainerSpec
    fun createKontainer(): T
    fun createKontainer(kontainerSpec: KontainerSpec): T
    fun supports(kontainerKClass: KClass<Kontainer>): Boolean
}
