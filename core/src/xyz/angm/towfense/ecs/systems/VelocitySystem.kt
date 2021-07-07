/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 3:44 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import com.badlogic.gdx.math.Vector2
import xyz.angm.rox.Entity
import xyz.angm.rox.Family
import xyz.angm.rox.systems.IteratingSystem
import xyz.angm.towfense.ecs.components.PositionComponent
import xyz.angm.towfense.ecs.components.VelocityComponent
import xyz.angm.towfense.ecs.position
import xyz.angm.towfense.ecs.velocity

class VelocitySystem : IteratingSystem(Family.allOf(PositionComponent::class, VelocityComponent::class)) {

    private val tmpV = Vector2()

    override fun process(entity: Entity, delta: Float) {
        entity[position].add(tmpV.set(entity[velocity]).scl(delta))
    }
}