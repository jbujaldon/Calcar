package com.calcar.common.core.wrappers

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorWrapper(val value: ULong) {
    constructor(color: Int) : this(color.toULong() shl 32)
    constructor(
        red: Int,
        green: Int,
        blue: Int,
        alpha: Int = 0xFF,
    ) : this(
        ((alpha and 0xFF) shl 24) or
                ((red and 0xFF) shl 16) or
                ((green and 0xFF) shl 8) or
                (blue and 0xFF),
    )

    companion object {
        operator fun invoke(color: Long): ColorWrapper =
            ColorWrapper((color.toULong() and 0xffffffffUL) shl 32)
    }
}

@Stable
fun ColorWrapper.toCompose() = Color(value)