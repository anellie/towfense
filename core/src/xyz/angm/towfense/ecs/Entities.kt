/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 3:50 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import xyz.angm.rox.Engine
import xyz.angm.towfense.ecs.components.*
import xyz.angm.towfense.graphics.actors.Turret
import xyz.angm.towfense.resources.Assets

fun createEnemy(engine: Engine) = engine.entity {
    with<PositionComponent>()
    with<PathedComponent>()
    with<DisplayComponent> {
        actor = Image(Assets.tex("entity/enemy"))
        actor.setSize(1f, 1f)
        actor.setOrigin(Align.center)
        actor.addAction(Actions.repeat(-1, Actions.rotateBy(360f, 1f)))
    }
    with<EnemyComponent>()
}

fun createTurret(engine: Engine, pos: Vector2) = engine.entity {
    val pos = with<PositionComponent> { set(pos) }
    val turret = with<TurretComponent>()
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
}