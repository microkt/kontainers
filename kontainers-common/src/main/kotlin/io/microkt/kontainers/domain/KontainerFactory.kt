package io.microkt.kontainers.domain

import kotlin.reflect.KClass

/**
 * Factory for creating [Kontainer]s of type [T].
 *
 * @author Scott Rossillo
 */
interface KontainerFactory<T : Kontainer> {
    val kontainerSpec: KontainerSpec

    /**
     * Returns an instance of [Kontainer] [T] created with
     * the default [kontainerSpec].
     */
    fun createKontainer(): T

    /**
     * Returns an instance of [Kontainer] [T] with the given
     * customized [kontainerSpec].
     *
     * @param kontainerSpec a customized [KontainerSpec] for
     * [Kontainer] [T]
     */
    fun createKontainer(kontainerSpec: KontainerSpec): T

    /**
     * Returns true of this [KontainerFactory] is capable of
     * instantiating a [Kontainer] of the given [kontainerKClass].
     */
    fun supports(kontainerKClass: KClass<Kontainer>): Boolean
}
