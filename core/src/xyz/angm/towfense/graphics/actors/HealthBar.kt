/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 6:58 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.actors

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.kotcrab.vis.ui.VisUI

/** A simple health bar.  */
class HealthBar(var health: Int, var max: Int) : Actor() {

    override fun draw(batch: Batch, parentAlpha: Float) {
        if (health == max) return  // Bar is full, don't show
        VisUI.getSkin().getDrawable("black-transparent").draw(batch, x, y, 1f, 0.1f)
        VisUI.getSkin().getDrawable("green").draw(batch, x, y, health.toFloat() / max, 0.1f)
    }
}