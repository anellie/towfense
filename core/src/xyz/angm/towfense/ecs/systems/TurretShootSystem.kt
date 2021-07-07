/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 3:49 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import com.badlogic.gdx.math.Vector2
import xyz.angm.rox.Entity
import xyz.angm.rox.Family
import xyz.angm.rox.systems.IntervalIteratingSystem
import xyz.angm.towfense.ecs.components.TurretComponent
import xyz.angm.towfense.ecs.createBullet
import xyz.angm.towfense.ecs.position
import xyz.angm.towfense.ecs.turret

private const val BULLET_SPEED = 10f

class TurretShootSystem : IntervalIteratingSystem(0.2f, Family.allOf(TurretComponent::class)) {

    private val tmpV = Vector2()

    override fun process(entity: Entity) {
        val pos = entity[position]
        val turret = entity[turret]
        if (!turret.hasTarget) return

        val vel = tmpV.set(turret.target).sub(pos).nor().scl(BULLET_SPEED)
        createBullet(engine, pos, vel)
    }
}