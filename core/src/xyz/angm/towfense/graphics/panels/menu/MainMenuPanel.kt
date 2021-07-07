/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/8/21, 12:33 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.panels.menu

import com.badlogic.gdx.Gdx
import ktx.actors.onClick
import ktx.actors.plusAssign
import ktx.scene2d.image
import ktx.scene2d.scene2d
import ktx.scene2d.vis.visTable
import xyz.angm.towfense.graphics.Skin
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.screens.MenuScreen
import xyz.angm.towfense.resources.I18N

/** Main menu panel. */
class MainMenuPanel(screen: MenuScreen) : Panel(screen) {

    init {
        reload(screen)
    }

    internal fun reload(screen: MenuScreen) {
        clearChildren()
        this += scene2d.visTable {
            pad(0f, 0f, 100f, 0f)

            image("logo") {
                it.height(300f).width(800f).pad(20f).row()
            }

            visTextButton(I18N["main.new-game"]) {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).row()
                onClick { screen.startNewGame() }
            }
            visTextButton(I18N["main.load-game"]) {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).row()
                onClick { }
            }
            visTextButton(I18N["main.editor"]) {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).row()
                onClick { screen.editor() }
            }
            visTextButton(I18N["main.options"]) {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).row()
                onClick { screen.pushPanel(OptionsPanel(screen, this@MainMenuPanel)) }
            }
            visTextButton(I18N["main.exit"]) {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).row()
                onClick { Gdx.app.exit() }
            }

            setFillParent(true)
        }
    }
}