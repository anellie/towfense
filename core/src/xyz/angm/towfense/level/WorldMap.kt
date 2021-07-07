/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 2:45 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.level

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import xyz.angm.towfense.IntVector
import xyz.angm.towfense.resources.Assets

class WorldMap private constructor(val path: Path) : Group() {

    fun drawBackground() {
        val pathImage = Assets.tex("map/path")
        val region = TextureRegion(pathImage)

        val location = path.start.cpy()
        var delay = 0.1f
        for (s in path.segments) {
            for (i in 0..s[LEN]) {
                plotPoint(pathImage, location, s[DIR], delay)
                Direction.add(location, s[DIR], 1)
                delay += 0.1f
            }
        }
    }

    private fun plotPoint(tex: Texture, loc: IntVector, dir: Int, delay: Float) {
        val actor = Image(TextureRegion(tex))
        actor.rotation = dir * 90f
        actor.setSize(1f, 1f)
        actor.setOrigin(Align.center)
        actor.x = loc.x.toFloat()
        actor.y = loc.y.toFloat()
        addActor(actor)

        actor.scaleX = 0f
        actor.scaleY = 0f
        actor.addAction(
            Actions.sequence(
                Actions.delay(delay),
                Actions.parallel(
                    Actions.scaleTo(1f, 1f, 0.5f, Interpolation.pow3),
                    Actions.rotateBy(360f, 0.5f, Interpolation.pow2)
                )
            )
        )
    }

    companion object {
        fun of(i: Int) = WorldMap(Levels[i])
    }
}

fun Vector2.div(x: Float, y: Float): Vector2 {
    this.x /= x
    this.y /= y
    return this
}