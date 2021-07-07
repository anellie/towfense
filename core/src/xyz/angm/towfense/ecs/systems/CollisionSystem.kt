/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 11:44 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import xyz.angm.rox.Entity
import xyz.angm.rox.Family
import xyz.angm.rox.systems.IteratingSystem
import xyz.angm.towfense.ecs.components.BulletComponent
import xyz.angm.towfense.ecs.components.EnemyComponent
import xyz.angm.towfense.ecs.display
import xyz.angm.towfense.ecs.enemy
import xyz.angm.towfense.ecs.position

class CollisionSystem : IteratingSystem(Family.allOf(EnemyComponent::class)) {

    private val bullets = Family.allOf(BulletComponent::class)

    override fun process(entity: Entity, delta: Float) {
        val pos = entity[position]
        val enemy = entity[enemy]

        for (bullet in engine[bullets]) {
            val bPos = bullet[position]
            if (bPos.x.toInt() == pos.x.toInt() && bPos.y.toInt() == pos.y.toInt()) {
                bullet.c(display)?.actor?.remove()
                engine.remove(bullet)
                enemy.health--
            }
        }

        if (enemy.health <= 0) {
            entity.c(display)?.actor?.remove()
            engine.remove(entity)
        }
    }
}