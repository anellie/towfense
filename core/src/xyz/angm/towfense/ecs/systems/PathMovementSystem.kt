/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 1:15 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import com.badlogic.gdx.math.MathUtils
import com.badlogic.gdx.math.Vector2
import xyz.angm.rox.Entity
import xyz.angm.rox.Family
import xyz.angm.rox.systems.IteratingSystem
import xyz.angm.towfense.ecs.components.PathedComponent
import xyz.angm.towfense.ecs.components.PositionComponent
import xyz.angm.towfense.ecs.pathed
import xyz.angm.towfense.ecs.position
import xyz.angm.towfense.level.DIR
import xyz.angm.towfense.level.Direction
import xyz.angm.towfense.level.LEN
import xyz.angm.towfense.level.Path

private const val SPEED = 3f

class PathMovementSystem(private val path: Path, private val endReached: (Entity) -> Unit) : IteratingSystem(
    Family.allOf(
        PathedComponent::class,
        PositionComponent::class
    )
) {

    override fun process(entity: Entity, delta: Float) {
        val posC = entity[position]
        val pathC = entity[pathed]
        pathC.distTravelled += delta * SPEED

        val segment = path.segments[pathC.segment]
        Direction.add(posC, segment[DIR], delta * SPEED)

        if (pathC.distTravelled >= (segment[LEN] + 1)) {
            posC.round()
            pathC.segment++
            pathC.distTravelled = 0f

            if (path.segments.size == pathC.segment) {
                endReached(entity)
                pathC.segment--
            }
        }
    }
}

private fun Vector2.round() {
    x = MathUtils.round(x).toFloat()
    y = MathUtils.round(y).toFloat()
}