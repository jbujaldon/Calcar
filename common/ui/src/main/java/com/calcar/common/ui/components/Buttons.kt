package com.calcar.common.ui.components

import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun GenericFAB(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        text = { Text(text = text) },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = null,
            )
        },
        modifier = modifier,
    )
}