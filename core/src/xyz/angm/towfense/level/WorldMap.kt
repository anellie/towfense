/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 6:59 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.level

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.math.Interpolation
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.actions.Actions
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import xyz.angm.towfense.IntVector
import xyz.angm.towfense.resources.Assets

class WorldMap private constructor(val path: Path) : Group() {

    private val tmpV = Vector2()
    private val tmpIV = IntVector()
    private val preview = PlacementPreview()
    private val occupiedLocations = HashMap<IntVector, Actor>()

    init {
        val straightImage = Assets.tex("map/straight_path")
        val cornerImage = Assets.tex("map/corner")
        val startImage = Assets.tex("map/start")
        val crossingDrawable = TextureRegionDrawable(TextureRegion(Assets.tex("map/crossing")))

        val location = path.start.cpy()
        var delay = 0.02f
        var last = path.segments[0][DIR]
        for (s in path.segments) {
            // todo whomst
            val actualCornerImage = if (s === path.segments[0]) startImage else cornerImage
            val isLeft = (last < s[DIR] && !(last == 0 && s[DIR] == 3)) || (last == 3 && s[DIR] == 0)
            plotPoint(actualCornerImage, location, s[DIR] + if (isLeft) 2 else 1, delay)
            Direction.add(location, s[DIR], 1)
            for (i in 1..s[LEN]) {
                if (isOccupied(location)) {
                    (occupiedLocations[location] as Image).drawable = crossingDrawable
                } else {
                    plotPoint(straightImage, location, s[DIR], delay)
                }
                Direction.add(location, s[DIR], 1)
                delay += 0.02f
            }
            last = s[DIR]
        }

        addActor(preview)
    }

    fun isOccupied(pos: IntVector) = occupiedLocations.contains(pos)

    fun setOccupied(pos: IntVector, actor: Actor) = occupiedLocations.put(pos, actor)

    fun updatePlacementPreview(x: Int, y: Int, kind: TurretKind?) {
        if (kind != null) {
            tmpV.set(x.toFloat(), y.toFloat())
            stage.screenToStageCoordinates(tmpV)
            tmpIV.set(tmpV)
            preview.update(tmpIV, kind, !isOccupied(tmpIV))
            preview.isVisible = true
        } else {
            preview.isVisible = false
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

        setOccupied(loc.cpy(), actor)
    }

    companion object {
        fun of(i: Int) = WorldMap(Levels[i])
    }
}
