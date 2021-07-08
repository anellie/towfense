/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 1:35 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.level

import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import ktx.assets.file
import xyz.angm.towfense.resources.Assets
import xyz.angm.towfense.resources.I18N
import xyz.angm.towfense.yaml

@Serializable
data class TurretKind(
    val identifier: String,
    val cost: Int,
    val aiming: Aiming,
    val shots: Shooting,
    val shotCount: Int = 1,
    val interval: Float,
    val range: Float,
) {

    val name get() = I18N["turret.name.${identifier}"]
    val description get() = I18N["turret.desc.${identifier}"]
    val baseTex get() = Assets.tex("entity/turret/${identifier}_base")
    val armTex get() = Assets.tex("entity/turret/${identifier}_arm")

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