/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 3:13 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.components

import com.badlogic.gdx.math.Vector2
import xyz.angm.rox.Component

class TurretComponent : Component {
    val target = Vector2()
}