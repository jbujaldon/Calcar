package com.calcar.common.ui.snackbar

import androidx.compose.material3.SnackbarDuration
import com.calcar.common.core.wrappers.TextWrapper

data class SnackbarState(
    val message: TextWrapper? = null,
    val duration: SnackbarDuration = SnackbarDuration.Short
)
