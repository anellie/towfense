/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 7:17 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import xyz.angm.rox.Entity
import xyz.angm.rox.Family
import xyz.angm.rox.systems.IntervalIteratingSystem
import xyz.angm.towfense.ecs.components.PositionComponent
import xyz.angm.towfense.ecs.display
import xyz.angm.towfense.ecs.position

class OOBRemoveSystem : IntervalIteratingSystem(0.5f, Family.allOf(PositionComponent::class)) {

    override fun process(entity: Entity) {
        val pos = entity[position]
        if (pos.x <= -200f || pos.x >= 1000f || pos.y <= -200f || pos.y >= 1000f) {
            entity.c(display)?.actor?.remove()
            engine.remove(entity)
        }
    }
}