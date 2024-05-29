package com.calcar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.calcar.common.ui.theme.CalcarTheme
import com.calcar.ui.CalcarApp
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        var initialLoadState: InitialLoadState by mutableStateOf(InitialLoadState.Loading)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.initialLoadState
                    .onEach { initialLoadState = it }
                    .collect()
            }
        }
        splashScreen.setKeepOnScreenCondition {
            when (initialLoadState) {
                is InitialLoadState.Loading -> true
                is InitialLoadState.Success -> false
            }
        }

        enableEdgeToEdge()

        setContent {
            CalcarTheme {
                val isOnboardingCompleted = isOnboardingCompleted(initialLoadState)
                if (isOnboardingCompleted != null) {
                    CalcarApp(isOnboardingCompleted)
                }
            }
        }
    }
}

@Composable
private fun isOnboardingCompleted(initialLoadState: InitialLoadState): Boolean? =
    when (initialLoadState) {
        is InitialLoadState.Loading -> null
        is InitialLoadState.Success -> initialLoadState.isOnboardingCompleted
    }
