/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/5/21, 9:31 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.panels.menu

import ktx.actors.onChange
import ktx.actors.onClick
import ktx.actors.plusAssign
import ktx.scene2d.scene2d
import ktx.scene2d.vis.visSelectBoxOf
import ktx.scene2d.vis.visTable
import xyz.angm.towfense.graphics.Skin
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.screens.MenuScreen
import xyz.angm.towfense.graphics.screens.Screen
import xyz.angm.towfense.resources.I18N
import xyz.angm.towfense.resources.configuration

/** Main options menu. */
class OptionsPanel(screen: Screen, parent: MainMenuPanel? = null) : Panel(screen) {

    init {
        reload(screen, parent)
    }

    private fun reload(screen: Screen, parent: MainMenuPanel?) {
        clearChildren()
        this += scene2d.visTable {
            // Only show certain options on menu screen
            if (screen is MenuScreen) {
                val box = visSelectBoxOf(I18N.languages())
                box.selected = configuration.language
                box.onChange {
                    I18N.setLanguage(box.selected)
                    parent!!.reload(screen)
                    reload(screen, parent)
                    this@OptionsPanel.isVisible = true // Regrab focus lost by reload
                }
                box.inCell.colspan(2)
                row()
            }

            visTextButton(I18N["options.controls"]) {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).colspan(2).row()
                onClick { screen.pushPanel(ControlsPanel(screen)) }
            }

            visTextButton(I18N["back"]) {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).colspan(2)
                onClick { screen.popPanel() }
            }

            setFillParent(true)
        }
    }

    override fun dispose() = configuration.save()
}