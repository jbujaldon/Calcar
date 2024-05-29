package com.calcar.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Tag(
    text: String,
    color: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier
            .clip(CircleShape)
            .background(color.copy(alpha = 0.34f))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.labelSmall,
    )
}