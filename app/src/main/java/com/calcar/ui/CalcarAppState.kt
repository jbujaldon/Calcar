package com.calcar.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberCalcarAppState(
    navController: NavHostController = rememberNavController(),
): CalcarAppState = remember(navController) {
    CalcarAppState(navController)
}

class CalcarAppState(val navController: NavHostController)