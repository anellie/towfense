/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/5/21, 11:48 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.graphics

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.kotcrab.vis.ui.VisUI
import com.kotcrab.vis.ui.widget.VisTextButton
import ktx.assets.disposeSafely
import ktx.assets.file
import ktx.scene2d.Scene2DSkin
import ktx.style.*
import xyz.angm.towfense.resources.Assets

/** The skin used for all UI objects in the client. */
object Skin {

    /** The height of text buttons in most menus. */
    const val textButtonHeight = 48f

    /** The width of text buttons in most menus. */
    const val textButtonWidth = 400f

    private val fontSizes = listOf(48, 32, 24, 16)
    private val colors5 = mapOf(
        Pair("white", Color.WHITE),
        Pair("light-grey", Color.LIGHT_GRAY),
        Pair("black-transparent", Color(0f, 0f, 0f, 0.5f)),
        Pair("red-transparent", Color(0.3f, 0f, 0f, 0.5f)),
        Pair("black", Color.BLACK),
        Pair("dark-grey", Color.DARK_GRAY),
        Pair("transparent", Color(0f, 0f, 0f, 0f)),
        Pair("dark-green", Color(0.3f, 0.4f, 0.3f, 1f))
    )
    private val colors32 = mapOf(
        Pair("item-selector", Color(1f, 1f, 1f, 0.5f)),
        Pair("red", Color.RED),
        Pair("green", Color.GREEN)
    )

    /** Reload the skin. Only needs to be called on init or when the resource pack changes. */
    fun reload() {
        val fontGen = FreeTypeFontGenerator(file("font/regular.ttf"))
        val parameter = FreeTypeFontGenerator.FreeTypeFontParameter()
        parameter.shadowColor = Color(0.4f, 0.4f, 0.4f, 0.8f)

        VisUI.getSkin().disposeSafely()
        VisUI.dispose(false)
        VisUI.load()
        val it = VisUI.getSkin()
        Scene2DSkin.defaultSkin = VisUI.getSkin().apply {
            fontSizes.forEach { size ->
                parameter.size = size
                parameter.shadowOffsetX = size / 10
                parameter.shadowOffsetY = size / 10

                val regular = fontGen.generateFont(parameter)
                regular.data.markupEnabled = true

                add("default-${size}pt", regular)
            }
            add("default", get<BitmapFont>("default-32pt"))

            colors5.forEach { color ->
                val pixmap = Pixmap(5, 5, Pixmap.Format.RGBA8888)
                pixmap.setColor(color.value)
                pixmap.fill()
                add(color.key, Texture(pixmap))
            }
            colors32.forEach { color ->
                val pixmap = Pixmap(32, 32, Pixmap.Format.RGBA8888)
                pixmap.setColor(color.value)
                pixmap.fill()
                add(color.key, Texture(pixmap))
            }

            add("logo", Assets.tex("ui/logo"))

            add("vis-default", get<VisTextButton.VisTextButtonStyle>())
            visTextButton {
                font = it["default-32pt"]
            }

            add("vis-default", get<Label.LabelStyle>())

            getAll(BitmapFont::class.java).forEach { skinFont ->
                label(skinFont.key) { font = skinFont.value }
            }

            progressBar("default-horizontal") {
                background = it["light-grey"]
                knobBefore = it["white"]
            }

            textField {
                font = it["default-32pt"]
                fontColor = Color.WHITE
                background = it["dark-grey"]
                cursor = it["white"]
                selection = it["dark-grey"]
            }

            button {
                up = it["black"]
                over = it["dark-grey"]
                checked = it["dark-green"]
            }

            scrollPane {}
        }

        fontGen.dispose()
    }
}


