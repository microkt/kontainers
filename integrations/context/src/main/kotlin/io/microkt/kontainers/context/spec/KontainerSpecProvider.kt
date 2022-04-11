package io.microkt.kontainers.context.spec

import io.microkt.kontainers.domain.KontainerSpec

/**
 * Provides a platform neutral technique for customizing a
 * [KontainerSpec].
 *
 * @author Scott Rossillo
 */
interface KontainerSpecProvider {
    fun override(kontainerSpec: KontainerSpec): KontainerSpec
}
