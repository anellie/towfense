/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/11/21, 2:10 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense

import com.badlogic.gdx.math.Vector2
import kotlinx.serialization.Serializable

@Serializable
data class IntVector(var x: Int, var y: Int) {

    constructor() : this(0, 0)
    constructor(v2: Vector2) : this() {
        set(v2)
    }

    fun add(s: IntVector): IntVector {
        x += s.x
        y += s.y
        return this
    }

    fun sub(s: IntVector): IntVector {
        x -= s.x
        y -= s.y
        return this
    }

    fun mul(s: IntVector): IntVector {
        x *= s.x
        y *= s.y
        return this
    }

    fun div(x: Int, y: Int): IntVector {
        this.x /= x
        this.y /= y
        return this
    }

    fun cpy() = IntVector(x, y)

    fun v2() = Vector2(x.toFloat(), y.toFloat())

    fun set(x: Int, y: Int): IntVector {
        this.x = x
        this.y = y
        return this
    }

    fun set(v2: Vector2): IntVector {
        x = v2.x.toInt()
        y = v2.y.toInt()
        return this
    }
}