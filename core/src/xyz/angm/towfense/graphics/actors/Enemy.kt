/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 8:17 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.actors

import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import xyz.angm.towfense.ecs.components.EnemyComponent
import xyz.angm.towfense.resources.Assets

class Enemy(private val enemy: EnemyComponent) : Group() {

    private val bar = HealthBar(enemy.health, enemy.maxHealth)

    init {
        val actor = Image(Assets.tex("entity/enemy"))
        actor.setSize(1f, 1f)
        actor.setOrigin(Align.center)
        actor.addAction(Actions.repeat(-1, Actions.rotateBy(360f, 1f)))
        addActor(actor)
        addActor(bar)
    }

    override fun act(delta: Float) {
        super.act(delta)
        bar.health = enemy.health
    }
}