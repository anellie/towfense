/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 3:40 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.rox.systems

import xyz.angm.rox.Entity
import xyz.angm.rox.Family

/** A combination of [IteratingSystem] and [IntervalSystem].
 *
 * @param interval The interval in seconds.
 * @param family The family of entities to act on.
 * @param priority [EntitySystem.priority] */
abstract class IntervalIteratingSystem(
    private val interval: Float,
    private val family: Family,
    priority: Int = 0
) : IntervalSystem(interval, priority) {

    override fun run() {
        for (entity in engine[family]) {
            process(entity)
        }
    }

    /** This function is called for every entity in [family] every cycle.
     * @param entity The entity to process */
    abstract fun process(entity: Entity)
}