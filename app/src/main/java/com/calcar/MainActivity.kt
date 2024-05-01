package com.calcar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.calcar.common.ui.theme.CalcarTheme
import com.calcar.ui.CalcarApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalcarTheme {
                CalcarApp()
            }
        }
    }
}