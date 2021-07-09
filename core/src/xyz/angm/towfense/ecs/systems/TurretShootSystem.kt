/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 3:06 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import xyz.angm.rox.Entity
import xyz.angm.rox.Family
import xyz.angm.rox.systems.IteratingSystem
import xyz.angm.towfense.ecs.components.PositionComponent
import xyz.angm.towfense.ecs.components.TurretComponent
import xyz.angm.towfense.ecs.createBullet
import xyz.angm.towfense.ecs.position
import xyz.angm.towfense.ecs.turret
import xyz.angm.towfense.level.Shooting

const val BULLET_SPEED = 60f

class TurretShootSystem : IteratingSystem(Family.allOf(TurretComponent::class)) {

    private val tmpV = Vector2()

    override fun process(entity: Entity, delta: Float) {
        val turret = entity[turret]
        turret.timeUntilShot -= delta
        if (turret.timeUntilShot <= 0f) {
            shoot(entity[position], turret)
            turret.timeUntilShot += turret.kind.interval
        }
    }

    private fun shoot(pos: PositionComponent, turret: TurretComponent) {
        if (!turret.hasTarget) return
        when (turret.kind.shots) {
            Shooting.SingleShot -> {
                val vel = tmpV.set(turret.target).sub(pos).nor().scl(BULLET_SPEED)
                createBullet(engine, pos, vel)
            }

            Shooting.Allround -> {
                for (i in 0 until turret.kind.shotCount) {
                    val angle = (i.toFloat() / turret.kind.shotCount) * 360f
                    val vel = tmpV.set(MathUtils.cosDeg(angle), MathUtils.sinDeg(angle)).scl(BULLET_SPEED / 5f)
                    createBullet(engine, pos, vel)
                }
            }
        }
    }
}