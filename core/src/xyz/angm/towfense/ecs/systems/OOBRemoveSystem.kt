/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 1:15 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import xyz.angm.rox.Entity
import xyz.angm.rox.Family
import xyz.angm.rox.systems.IntervalIteratingSystem
import xyz.angm.towfense.ecs.components.PositionComponent
import xyz.angm.towfense.ecs.position
import xyz.angm.towfense.ecs.removeEntity

class OOBRemoveSystem : IntervalIteratingSystem(0.5f, Family.allOf(PositionComponent::class)) {

    override fun process(entity: Entity) {
        val pos = entity[position]
        if (pos.x <= -10f || pos.x >= 200f || pos.y <= -10f || pos.y >= 200f) {
            engine.removeEntity(entity)
        }
    }
}