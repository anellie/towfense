/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 7:08 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics.panels.menu

import com.badlogic.gdx.Input
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import ktx.actors.onClick
import ktx.actors.onKeyDown
import ktx.actors.plusAssign
import ktx.scene2d.scene2d
import ktx.scene2d.scrollPane
import ktx.scene2d.vis.visTable
import xyz.angm.towfense.actions.PlayerAction
import xyz.angm.towfense.graphics.Skin
import xyz.angm.towfense.graphics.panels.Panel
import xyz.angm.towfense.graphics.screens.Screen
import xyz.angm.towfense.resources.I18N
import xyz.angm.towfense.resources.configuration

/** Options submenu for controls. */
class ControlsPanel(private var screen: Screen) : Panel(screen) {

    private var current: Pair<Int, PlayerAction>? = null
    private var currentBtn: TextButton? = null
    private var table: Table

    init {
        this += scene2d.visTable {
            visTextButton(I18N["back"]) {
                it.height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).colspan(2).row()
                onClick { screen.popPanel() }
            }

            focusedActor = scrollPane {
                table = visTable {}

                onKeyDown { keycode ->
                    if (current == null && keycode == Input.Keys.ESCAPE) {
                        configuration.save()
                        screen.popPanel()
                    } else {
                        val current = current ?: return@onKeyDown
                        configuration.keybinds.unregisterKeybind(current.first)
                        configuration.keybinds.unregisterKeybind(keycode)
                        configuration.keybinds.registerKeybind(keycode, current.second.type)
                        this@ControlsPanel.current = null
                        updateBinds()
                    }
                }

                it.pad(50f, 0f, 50f, 0f).expand().row()
            }

            setFillParent(true)
        }
        clearListeners()
        updateBinds()
    }

    private fun updateBinds() {
        table.clearChildren()

        configuration.keybinds.getAllSorted().forEach { action ->
            val label = Label("${I18N["keybind.${action.second.type}"]}:", skin)
            table.add(label).pad(20f)

            val text = if (action.first == 0) "---" else Input.Keys.toString(action.first)
            val button = TextButton(text, skin)
            table.add(button).height(Skin.textButtonHeight).width(Skin.textButtonWidth).pad(20f).row()

            button.onClick {
                currentBtn?.label?.setColor(1f, 1f, 1f, 1f)
                if (current == action) {
                    current = null
                    currentBtn = null
                } else {
                    current = action
                    currentBtn = button
                    button.label.setColor(0f, 1f, 0f, 1f)
                }
            }
        }
    }
}