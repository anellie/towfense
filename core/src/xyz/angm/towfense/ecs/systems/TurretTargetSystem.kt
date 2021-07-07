/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 3:32 AM.
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
import xyz.angm.towfense.ecs.position
import xyz.angm.towfense.ecs.turret

class TurretTargetSystem : IteratingSystem(Family.allOf(TurretComponent::class)) {

    private val tmpV = Vector2()
    private val enemies = Family.allOf(EnemyComponent::class)

    override fun process(entity: Entity, delta: Float) {
        val tPos = entity[position]
        val turret = entity[turret]

        var leastDist = 99999999f
        var closestEnemy = entity
        for (enemy in engine[enemies]) {
            val ePos = enemy[position]
            val dist = tmpV.set(ePos).dst2(tPos)
            if (dist < leastDist) {
                leastDist = dist
                closestEnemy = enemy
            }
        }

        turret.target.set(closestEnemy[position])
        turret.hasTarget = closestEnemy !== entity
    }
}