/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/6/21, 12:28 AM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense

import ch.qos.logback.classic.Level
import com.badlogic.gdx.math.Vector2
import com.charleskorn.kaml.Yaml
import mu.KLogger
import mu.KotlinLogging

/** A simple YAML serializer used for configuration files and some game data. */
val yaml = Yaml()

/** Global logger. Log level can be set to DEBUG using --debug as first VM argument, otherwise it's WARN. */
val log = KotlinLogging.logger { }

/** Extension property for easily getting and setting log level. */
var KLogger.level: Level
    get() = (underlyingLogger as ch.qos.logback.classic.Logger).level
    set(value) {
        (underlyingLogger as ch.qos.logback.classic.Logger).level = value
    }

/** Turns a string into a long, by multiplying the byte of every char with it. Used for generating a Random seed. */
fun String.convertToLong(): Long {
    var long = 1L
    this.chars().forEach { long *= it }
    return long
}

/** Axis indexing for vectors. */
operator fun Vector2.get(index: Int) =
    when (index) {
        0 -> x
        else -> y
    }

/** Axis indexing for vectors. */
operator fun Vector2.set(index: Int, v: Float) =
    when (index) {
        0 -> x = v
        else -> y = v
    }

/** Run the given closure, catching and logging any exceptions that occur instead of
 * having them go up the call chain. The string parameters are used to generate
 * the accompanying error message, see the catch block. */
inline fun runLogE(user: String, activity: String, run: () -> Unit) {
    try {
        run()
    } catch (e: Exception) {
        log.warn(e) { "$user encountered an exception while $activity:" }
    }
}