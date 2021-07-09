/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 3:16 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import com.badlogic.gdx.scenes.scene2d.Stage
import xyz.angm.rox.Entity
import xyz.angm.rox.EntityListener
import xyz.angm.rox.Family
import xyz.angm.rox.systems.IteratingSystem
import xyz.angm.towfense.ecs.components.DisplayComponent
import xyz.angm.towfense.ecs.components.PositionComponent
import xyz.angm.towfense.ecs.display
import xyz.angm.towfense.ecs.position

class DisplaySystem(private val stage: Stage) : IteratingSystem(Family.allOf(PositionComponent::class, DisplayComponent::class)), EntityListener {

    override val family = Family.allOf(DisplayComponent::class)

    override fun process(entity: Entity, delta: Float) {
        val pos = entity[position]
        entity[display].actor.setPosition(pos.x, pos.y)
    }

    override fun entityAdded(entity: Entity) {
        stage.addActor(entity[display].actor)
        process(entity, 0f)
    }

    override fun entityRemoved(entity: Entity) {}
}