/*
 * Developed as part of the towfense project.
 * This file was last modified at 7/6/21, 8:19 PM.
 * Copyright 2020, see git repository at git.angm.xyz for authors and other info.
 * This file is under the GPL3 license. See LICENSE in the root directory of this repository for details.
 */

package xyz.angm.towfense.ecs.components

import xyz.angm.rox.Component

class PathedComponent : Component {
    var segment = 0
    var distLeft = 0f
}