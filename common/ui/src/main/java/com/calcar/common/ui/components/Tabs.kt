package com.calcar.common.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.calcar.common.ui.models.Tab

@Composable
fun TabsBar(
    currentTabIndex: Int,
    tabs: List<Tab>,
    onTabSelected: (Tab) -> Unit,
    modifier: Modifier = Modifier,
) {

    TabRow(selectedTabIndex = currentTabIndex){
        tabs.forEachIndexed { index, tab ->
            val isSelected = index == currentTabIndex
            Tab(
                selected = isSelected,
                onClick = { onTabSelected(tab) },
                modifier = modifier,
                text = { Text(text = stringResource(tab.labelRes)) },
                selectedContentColor = if (isSelected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        }
    }
}
