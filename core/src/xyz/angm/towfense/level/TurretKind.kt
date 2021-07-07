/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 10:28 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.level

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import ktx.assets.file
import xyz.angm.towfense.resources.Assets
import xyz.angm.towfense.yaml

@Serializable
data class TurretKind(
    val name: String,
    val aiming: Aiming,
    val shots: Shooting,
    val shotCount: Int = 1,
    val interval: Float,
    val range: Float,
) {

    val baseTex get() = Assets.tex("entity/turret/${name}_base")
    val armTex get() = Assets.tex("entity/turret/${name}_arm")

    companion object {
        private val turrets = yaml.decodeFromString(ListSerializer(serializer()), file("turrets.yaml").readString())

        operator fun get(index: Int) = turrets[index]
        fun all() = turrets
    }
}

enum class Aiming {
    None,
    Fixed,
    SingleEnemy
}

enum class Shooting {
    SingleShot,
    Allround
}