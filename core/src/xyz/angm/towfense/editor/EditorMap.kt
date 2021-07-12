/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/12/21, 6:46 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.editor

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Group
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.utils.Align
import ktx.actors.alpha
import ktx.collections.*
import xyz.angm.towfense.IntVector
import xyz.angm.towfense.level.*
import xyz.angm.towfense.resources.Assets
import xyz.angm.towfense.runLogE

class EditorMap private constructor(val path: Path) : Group() {

    private val segments = GdxArray<Group>()
    private val segmentEnds = GdxArray<IntVector>()
    private var tempSegment: IntArray? = null

    init {
        segmentEnds.add(path.start)
        for (segment in path.segments) {
            addSegment(segment, 1f)
        }
    }

    fun addSegment(segment: IntArray, trans: Float) {
        val pathImage = Assets.tex("ui/arrow")
        val location = segmentEnds[segmentEnds.size - 1].cpy()
        val group = Group()
        for (i in 0..segment[LEN]) {
            group.addActor(plotPoint(pathImage, location, segment[DIR], trans))
            Direction.add(location, segment[DIR], 1)
        }
        segments.add(group)
        segmentEnds.add(location)
        addActor(group)
    }

    fun setTempSegment(segment: IntArray) {
        addSegment(segment, 0.5f)
        tempSegment = segment
    }

    fun removeTempSegment() {
        if (tempSegment == null) return
        segments.pop().remove()
        segmentEnds.pop()
        tempSegment = null
    }

    fun removeLast() {
        if (tempSegment != null) removeTempSegment()
        runLogE("editor", "removing segment") {
            segments.pop().remove()
            segmentEnds.pop()
            path.segments.removeLast()
        }
    }

    fun materializeLast() {
        if (segments.size == 0) return
        for (actor in segments[segments.size - 1].children) {
            actor.alpha = 1f
        }
        path.segments.add(tempSegment ?: return)
        tempSegment = null
    }

    fun endOfSecondLast() = segmentEnds[segmentEnds.size - if (tempSegment == null) 1 else 2]!!

    private fun plotPoint(tex: Texture, loc: IntVector, dir: Int, trans: Float): Actor {
        val actor = Image(TextureRegion(tex))
        actor.rotation = dir * 90f
        actor.setSize(1f, 1f)
        actor.setOrigin(Align.center)
        actor.x = loc.x.toFloat()
        actor.y = loc.y.toFloat()
        actor.alpha = trans
        return actor
    }

    companion object {
        fun of(i: Int) = EditorMap(Levels[i])
    }
}
