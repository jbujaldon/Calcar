package com.calcar.common.core.wrappers

import android.content.res.Resources

sealed class TextWrapper {
    abstract fun getText(resources: Resources): String

    data class Literal(val text: String) : TextWrapper() {
        override fun getText(resources: Resources): String = text
    }

    class Resource(val res: Int, vararg val params: Any) : TextWrapper() {
        override fun getText(resources: Resources): String =
            if (params.isNotEmpty()) {
                val parsedParams = params.map { it }
                String.format(resources.getString(res), *parsedParams.toTypedArray())
            } else {
                String.format(resources.getString(res), *params)
            }

        override fun equals(other: Any?): Boolean =
            (other is Resource) && other.res == res && other.params.contentEquals(params)

        override fun hashCode(): Int {
            var result = res
            result = 31 * result + params.contentHashCode()
            return result
        }
    }
}