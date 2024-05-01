package com.calcar.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.calcar.navigation.CalcarNavHost

@Composable
fun CalcarApp() {
    val appState = rememberCalcarAppState()

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
    ) { innerPadding ->
        CalcarNavHost(
            appState = appState,
            modifier = Modifier.padding(innerPadding),
        )
    }
}