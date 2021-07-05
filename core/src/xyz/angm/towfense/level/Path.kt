/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/7/21, 2:05 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.level

import com.badlogic.gdx.math.Vector2
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import ktx.assets.file
import xyz.angm.towfense.IntVector
import xyz.angm.towfense.yaml

const val DIR = 0
const val LEN = 1

@Serializable
class Path {
    val start = IntVector()
    val segments = ArrayList<IntArray>()
    val mapSize = IntVector()
}

enum class Direction {
    North,
    West,
    South,
    East;

    companion object {
        fun add(vec: IntVector, dir: Int, len: Int): IntVector {
            when (dir) {
                0 -> vec.y += len
                1 -> vec.x -= len
                2 -> vec.y -= len
                else -> vec.x += len
            }
            return vec
        }

        fun add(vec: Vector2, dir: Int, len: Float): Vector2 {
            when (dir) {
                0 -> vec.y += len
                1 -> vec.x -= len
                2 -> vec.y -= len
                else -> vec.x += len
            }
            return vec
        }
    }
}

object Levels {
    private val levels = yaml.decodeFromString(ListSerializer(Path.serializer()), file("levels.yaml").readString())
    operator fun get(index: Int) = levels[index]
}