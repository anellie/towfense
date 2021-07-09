/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 1:59 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.window

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.utils.Align
import com.kotcrab.vis.ui.widget.VisLabel
import ktx.actors.onClick
import ktx.scene2d.button
import ktx.scene2d.scene2d
import xyz.angm.towfense.graphics.click
import xyz.angm.towfense.graphics.screens.GameScreen
import xyz.angm.towfense.level.TurretKind
import xyz.angm.towfense.resources.I18N


class TurretSelectWindow(screen: GameScreen) : Window("window.turret-select", false) {

    private val tooltip = BuildTooltip(this)

    init {
        val group = ButtonGroup<Button>()
        group.setMaxCheckCount(1)
        group.setMinCheckCount(0)

        for (kind in TurretKind.all()) {
            val btn = scene2d.button {
                add(Image(kind.baseTex))
                setSize(40f, 40f)
                onClick {
                    screen.inputHandler.currentKind = if (screen.inputHandler.currentKind === kind || screen.coins < kind.cost) null else kind
                }
                addListener(tooltip.getClickListener(kind, screen))
                click()
            }
            group.add(btn)
            add(btn).pad(3f)
        }

        width = 350f
    }

    override fun viewportResize() = setPosition(999999f, 0f)

    override fun setStage(stage: Stage?) {
        super.setStage(stage)
        stage?.addActor(tooltip)
    }

    private class BuildTooltip(private val parent: Actor) : Window("---", false) {

        init {
            isVisible = false
        }

        // Returns a ClickListener to be used with a button for the passed in turret kind
        fun getClickListener(turret: TurretKind, screen: GameScreen): ClickListener {
            return object : ClickListener() {
                override fun enter(event: InputEvent, x: Float, y: Float, pointer: Int, fromActor: Actor?) {
                    display(turret, screen.coins >= turret.cost)
                }

                override fun exit(event: InputEvent, x: Float, y: Float, pointer: Int, toActor: Actor?) {
                    isVisible = false
                }
            }
        }

        // Update and display this tooltip
        private fun display(kind: TurretKind, hasCoins: Boolean) {
            clear()
            titleLabel.setText(kind.name)
            val desc = VisLabel(kind.description)
            desc.wrap = true
            add(desc).colspan(2).width(300f).padBottom(20f).padTop(5f).row()
            val cost = VisLabel("${I18N["build-cost"]}:  ${kind.cost}")
            if (!hasCoins) cost.color = Color.RED
            add(cost).colspan(2).width(300f).row()
            add(VisLabel("${I18N["range"]}:  ${kind.range}")).colspan(2).width(300f).row()
            padBottom(5f)
            pack()
            width = parent.width

            if ((parent.y + parent.height + height) > stage.viewport.worldHeight) {
                setPosition(parent.x, parent.y, Align.topLeft)
            } else {
                setPosition(parent.x, parent.y + parent.height, Align.bottomLeft)
            }
            isVisible = true
        }

    }
}