/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 3:16 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.actors

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import xyz.angm.towfense.ecs.components.TurretComponent
import xyz.angm.towfense.resources.Assets

class Turret(private val comp: TurretComponent) : Group() {

    private val arm = Image(Assets.tex("entity/turret_arm"))

    init {
        val base = Image(Assets.tex("entity/turret_base"))
        base.setSize(1f, 1f)
        addActor(base)

        arm.setSize(1f, 1f)
        arm.setOrigin(Align.center)
        addActor(arm)
    }

    override fun act(delta: Float) {
        arm.rotation += delta * 10
        super.act(delta)
    }
}