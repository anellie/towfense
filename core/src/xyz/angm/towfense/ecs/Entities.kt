/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 6:46 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import xyz.angm.rox.Engine
import xyz.angm.rox.Entity
import xyz.angm.towfense.ecs.components.*
import xyz.angm.towfense.graphics.actors.Enemy
import xyz.angm.towfense.graphics.actors.Turret
import xyz.angm.towfense.level.TurretKind
import xyz.angm.towfense.resources.Assets

fun createEnemy(engine: Engine, startPos: Vector2) = engine.entity {
    with<PositionComponent> { set(startPos) }
    with<PathedComponent>()
    val enemy = with<EnemyComponent>()
    with<DisplayComponent> { actor = Enemy(enemy) }
}

fun createTurret(engine: Engine, pos: Vector2, kind: TurretKind) = engine.entity {
    val pos = with<PositionComponent> { set(pos) }
    val turret = with<TurretComponent> { this.kind = kind }
    with<DisplayComponent> {
        actor = Turret(pos, turret)
        actor.setSize(1f, 1f)
    }
}

fun createBullet(engine: Engine, pos: Vector2, vel: Vector2) = engine.entity {
    with<PositionComponent> { set(pos).add(0.5f, 0.5f) }
    with<VelocityComponent> { set(vel) }
    with<DisplayComponent> {
        actor = Image(Assets.tex("entity/bullet"))
        actor.setSize(0.1f, 0.2f)
        actor.setOrigin(Align.center)
        actor.rotation = vel.angleDeg() + 90f
    }
    with<BulletComponent>()
}

fun Engine.removeEntity(entity: Entity) {
    entity.c(display)?.actor?.remove()
    remove(entity)
}