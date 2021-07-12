/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 5:28 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import com.badlogic.gdx.math.Vector2
import xyz.angm.rox.Entity
import xyz.angm.rox.Family
import xyz.angm.rox.systems.IteratingSystem
import xyz.angm.towfense.ecs.components.EnemyComponent
import xyz.angm.towfense.ecs.components.TurretComponent
import xyz.angm.towfense.ecs.pathed
import xyz.angm.towfense.ecs.position
import xyz.angm.towfense.ecs.turret
import xyz.angm.towfense.level.Aiming
import xyz.angm.towfense.level.Path

class TurretTargetSystem(private val path: Path) : IteratingSystem(Family.allOf(TurretComponent::class)) {

    private val tmpV = Vector2()
    private val enemies = Family.allOf(EnemyComponent::class)

    override fun process(entity: Entity, delta: Float) {
        val tPos = entity[position]
        val turret = entity[turret]

        when (turret.kind.aiming) {
            Aiming.SingleEnemy -> {
                val closestEnemy = findClosest(entity, tPos)
                val dist = tmpV.set(closestEnemy[position]).dst(tPos)
                turret.target.set(calcEnemyPos(tPos, closestEnemy))
                turret.hasTarget = closestEnemy !== entity && dist <= turret.kind.range
            }

            else -> turret.hasTarget = anyEnemyInRange(tPos, turret.kind.range)
        }
    }

    private fun anyEnemyInRange(tPos: Vector2, range: Float): Boolean {
        for (enemy in engine[enemies]) {
            val ePos = enemy[position]
            val dist = tmpV.set(ePos).dst2(tPos)
            if (dist < (range * range)) return true
        }
        return false
    }

    private fun findClosest(turretE: Entity, tPos: Vector2): Entity {
        var leastDist = 99999999f
        var closestEnemy = turretE
        for (enemy in engine[enemies]) {
            val ePos = enemy[position]
            val dist = tmpV.set(ePos).dst2(tPos)
            if (dist < leastDist) {
                leastDist = dist
                closestEnemy = enemy
            }
        }

        return closestEnemy
    }

    // Calculate position at which a bullet must shoot to hit the given enemy.
    private fun calcEnemyPos(turretPos: Vector2, enemy: Entity): Vector2 {
        val posC = enemy[position].cpy()
        val pathC = enemy.c(pathed) ?: return posC

        val pathS = pathC.segment
        val pathD = pathC.distTravelled

        var delta = 0f
        var dist = 9999f
        while (delta < dist) {
            moveOnPath(path, posC, pathC, 0.16f)
            delta += 0.16f
            dist = turretPos.dst(posC) / BULLET_SPEED
        }

        pathC.segment = pathS
        pathC.distTravelled = pathD
        return posC
    }
}