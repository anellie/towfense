/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/9/21, 1:00 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.window

import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup
import com.kotcrab.vis.ui.widget.VisLabel
import ktx.actors.onClick
import ktx.scene2d.horizontalGroup
import ktx.scene2d.scene2d
import ktx.scene2d.vis.visLabel
import ktx.scene2d.vis.visTextButton
import ktx.scene2d.vis.visTextTooltip
import xyz.angm.towfense.graphics.click
import xyz.angm.towfense.graphics.screens.GameScreen
import xyz.angm.towfense.resources.I18N

class ControlsWindow(private val screen: GameScreen) : Window("window.controls", false) {

    private var coins = screen.coins
    private var lives = screen.lives
    private val coinLabel: VisLabel
    private val livesLabel: VisLabel

    init {
        add(scene2d.horizontalGroup {
            visLabel("${I18N["coins"]}    ")
            coinLabel = visLabel(coins.toString())
        }).colspan(4).row()
        add(scene2d.horizontalGroup {
            visLabel("${I18N["lives"]}    ")
            livesLabel = visLabel(lives.toString())
        }).colspan(4).row()

        val group = ButtonGroup<Button>()
        group.setMaxCheckCount(1)
        fun btn(multi: Float, name: String) {
            val btn = scene2d.visTextButton(name, "vis-default") {
                visTextTooltip("${multi}x")
                onClick { screen.gameSpeed = multi }
                click()
            }
            group.add(btn)
            add(btn).pad(3f)
        }
        btn(0f, "||")
        btn(1f, ">")
        btn(2f, ">>")
        btn(4f, ">>>>")

        pack()
        setPosition(999999f, 300f)
    }

    override fun act(delta: Float) {
        super.act(delta)
        if (coins != screen.coins) {
            coins = screen.coins
            coinLabel.setText(coins.toString())
        }
        if (lives != screen.lives) {
            lives = screen.lives
            livesLabel.setText(lives.toString())
        }
    }
}