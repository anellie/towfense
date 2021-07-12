/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 6:36 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.systems

import com.badlogic.gdx.math.Vector2
import xyz.angm.rox.systems.EntitySystem
import xyz.angm.towfense.ecs.createEnemy

class EnemySpawnSystem(private val startPos: Vector2, startThresh: Float, private val decrement: Float) : EntitySystem() {

    private var thresh = startThresh
    private var counter = 0f

    override fun update(delta: Float) {
        counter += delta
        if (counter > thresh) {
            counter = 0f
            thresh *= decrement
            createEnemy(engine, startPos)
        }
    }
}