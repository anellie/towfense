/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 11:01 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.actors

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import xyz.angm.towfense.ecs.components.PositionComponent
import xyz.angm.towfense.ecs.components.TurretComponent

class Turret(private val pos: PositionComponent, private val comp: TurretComponent) : Group() {

    private val tmpV = Vector2()
    private val arm = Image(comp.kind.armTex)

    init {
        val base = Image(comp.kind.baseTex)
        base.setSize(1f, 1f)
        addActor(base)

        arm.setSize(1f, 1f)
        arm.setOrigin(Align.center)
        addActor(arm)
    }

    override fun act(delta: Float) {
        arm.rotation = if (comp.hasTarget) tmpV.set(pos).sub(comp.target).angleDeg() + 90f
        else arm.rotation + delta * 100
        super.act(delta)
    }
}