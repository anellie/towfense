/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 4:53 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.panels.game

import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import ktx.actors.onClick
import ktx.actors.plusAssign
import ktx.scene2d.button
import ktx.scene2d.scene2d
import ktx.scene2d.vis.visLabel
import ktx.scene2d.vis.visTable
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.screens.GameScreen
import xyz.angm.towfense.resources.Assets
import xyz.angm.towfense.resources.I18N

class DirectionSelectPanel(screen: GameScreen, result: (Int) -> Unit) : Panel(screen) {

    init {
        this += scene2d.visTable {
            visLabel(I18N["choose-direction"], "default-32pt") {
                it.padBottom(100f).colspan(4).row()
            }

            fun btn(rot: Float, res: Int) {
                button {
                    val img = Image(Assets.tex("ui/arrow"))
                    img.setOrigin(Align.center)
                    img.rotation = rot
                    add(img)
                    onClick {
                        result(res)
                        screen.popPanel()
                    }
                }
            }

            btn(90f, 1)
            btn(0f, 0)
            btn(180f, 2)
            btn(270f, 3)

            setFillParent(true)
        }
    }
}