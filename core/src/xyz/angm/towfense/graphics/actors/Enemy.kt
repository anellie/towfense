/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 6:57 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.actors

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import xyz.angm.towfense.resources.Assets

class Enemy : Group() {

    init {
        val actor = Image(Assets.tex("entity/enemy"))
        actor.setSize(1f, 1f)
        actor.setOrigin(Align.center)
        actor.addAction(Actions.repeat(-1, Actions.rotateBy(360f, 1f)))
        addActor(actor)

        val bar = HealthBar(2, 5)
        addActor(bar)
    }
}