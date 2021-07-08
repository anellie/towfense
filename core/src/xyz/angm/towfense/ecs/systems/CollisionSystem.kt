/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 1:15 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import xyz.angm.rox.Entity
import xyz.angm.rox.Family
import xyz.angm.rox.systems.IteratingSystem
import xyz.angm.towfense.ecs.components.BulletComponent
import xyz.angm.towfense.ecs.components.EnemyComponent
import xyz.angm.towfense.ecs.enemy
import xyz.angm.towfense.ecs.position
import xyz.angm.towfense.ecs.removeEntity

class CollisionSystem(private val enemyKilled: (Entity) -> Unit) : IteratingSystem(Family.allOf(EnemyComponent::class)) {

    private val bullets = Family.allOf(BulletComponent::class)

    override fun process(entity: Entity, delta: Float) {
        val pos = entity[position]
        val enemy = entity[enemy]

        for (bullet in engine[bullets]) {
            val bPos = bullet[position]
            if (bPos.x.toInt() == pos.x.toInt() && bPos.y.toInt() == pos.y.toInt()) {
                engine.removeEntity(bullet)
                enemy.health--
            }
        }

        if (enemy.health <= 0) enemyKilled(entity)
    }
}