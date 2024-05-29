package com.calcar.common.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.calcar.common.ui.models.Tab

@Composable
fun TopBarWithTabs(
    title: String,
    currentTabIndex: Int,
    tabs: List<Tab>,
    onTabSelected: (Tab) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        CenterTitleTopBar(title = title)
        TabsBar(
            currentTabIndex = currentTabIndex,
            tabs = tabs,
            onTabSelected = onTabSelected,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CenterTitleTopBar(
    title: String,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        modifier = modifier
    )
}