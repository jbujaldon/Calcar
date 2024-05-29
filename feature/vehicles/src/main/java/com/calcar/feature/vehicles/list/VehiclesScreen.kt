package com.calcar.feature.vehicles.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.North
import androidx.compose.material.icons.filled.South
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calcar.common.core.wrappers.toCompose
import com.calcar.common.domain.vehicles.entities.Vehicle
import com.calcar.common.ui.components.GenericFAB
import com.calcar.common.ui.components.Tag
import com.calcar.common.ui.components.TopBarWithTabs
import com.calcar.common.ui.models.Tab
import com.calcar.common.ui.utils.toEuroCurrency
import com.calcar.feature.vehicles.R
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun VehiclesScreen(viewModel: VehiclesViewModel = koinViewModel()) {
    val tab by viewModel.currentTab.collectAsStateWithLifecycle()
    val vehicles by viewModel.vehicles.collectAsStateWithLifecycle()

    VehiclesContent(
        currentTab = tab,
        onTabSelected = viewModel::onTabChanged,
        onAddVehicle = {},
        vehicles = vehicles
    )
}

@Suppress("UNCHECKED_CAST")
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun VehiclesContent(
    currentTab: VehicleTab,
    onTabSelected: (Tab) -> Unit,
    onAddVehicle: () -> Unit,
    vehicles: List<VehicleUi>,
) {
    val pagerState = rememberPagerState { VehicleTab.entries.size }

    Scaffold(
        topBar = {
            VehiclesTopBar(
                currentTab = currentTab,
                onTabSelected = onTabSelected,
            )
        },
        floatingActionButton = {
           VehiclesFAB(onClickAddVehicle = onAddVehicle)
        },
    ) { innerPadding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(innerPadding),
        ) { index ->
            when (val tab = VehicleTab.entries[index]) {
                VehicleTab.Budgeted -> BudgetedVehiclesListContent(
                    vehicles = vehicles as List<VehicleUi.Budgeted>,
                    onClickVehicle = {},
                )
                else -> OtherVehiclesListContent(
                    vehicles = vehicles as List<VehicleUi.Other>,
                    onClickVehicle = {},
                    tab = tab
                )
            }
        }
    }
}

@Composable
private fun VehiclesTopBar(
    currentTab: VehicleTab,
    onTabSelected: (Tab) -> Unit,
    modifier: Modifier = Modifier,
) {
    TopBarWithTabs(
        title = stringResource(R.string.vehicles_title),
        currentTabIndex = currentTab.ordinal,
        tabs = VehicleTab.entries,
        onTabSelected = onTabSelected,
        modifier = modifier,
    )
}

@Composable
private fun VehiclesFAB(
    onClickAddVehicle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    GenericFAB(
        icon = Icons.Outlined.Edit,
        text = stringResource(R.string.add_vehicle_button),
        onClick = onClickAddVehicle,
        modifier = modifier,
    )
}

@Composable
private fun BudgetedVehiclesListContent(
    vehicles: List<VehicleUi.Budgeted>,
    onClickVehicle: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    if (vehicles.isEmpty()) {
        EmptyListContent(
            message = stringResource(R.string.empty_budgeted_vehicles_list),
            modifier = modifier.fillMaxSize(),
        )
    } else {
        BudgetedVehiclesList(
            vehicles = vehicles,
            onClickVehicle = onClickVehicle,
            modifier = modifier,
        )
    }
}

@Composable
private fun EmptyListContent(
    message: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.empty_vehicles),
            contentDescription = null,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = message,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun BudgetedVehiclesList(
    vehicles: List<VehicleUi.Budgeted>,
    onClickVehicle: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(vehicles) { vehicle ->
            VehicleItem(
                vehicle = vehicle,
                onClick = { onClickVehicle(vehicle.id) },
            )
        }
    }
}

@Composable
private fun OtherVehiclesListContent(
    vehicles: List<VehicleUi.Other>,
    onClickVehicle: (Long) -> Unit,
    tab: VehicleTab,
    modifier: Modifier = Modifier,
) {
    if (vehicles.isEmpty()) {
        val text = when {
            tab == VehicleTab.Repairing -> stringResource(R.string.empty_repairing_vehicles_list)
            else -> stringResource(R.string.empty_finished_vehicles_list)
        }
        EmptyListContent(
            message = text,
            modifier = modifier.fillMaxSize(),
        )
    } else {
        OtherVehiclesList(
            vehicles = vehicles,
            onClickVehicle = onClickVehicle,
            modifier = modifier,
        )
    }
}

@Composable
private fun OtherVehiclesList(
    vehicles: List<VehicleUi.Other>,
    onClickVehicle: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(vehicles) { vehicle ->
            VehicleItem(
                vehicle = vehicle,
                onClick = { onClickVehicle(vehicle.id) },
            )
        }
    }
}

@Composable
private fun VehicleItem(
    vehicle: VehicleUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    with(vehicle) {
        Row(
            modifier = modifier.clickable { onClick() },
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            VehicleImage()
            VehicleMainInfo(
                makeModel = makeModel,
                plateNumber = plateNumber,
                ownerName = ownerName,
            )
        }
        when (vehicle) {
            is VehicleUi.Budgeted -> BudgetedExtraInfo(
                isPending = vehicle.isPending,
                date = modificationDate,
                benefitAmount = vehicle.benefitAmount,
            )
            is VehicleUi.Other -> OtherExtraInfo(
                referenceNumber = vehicle.referenceNumber,
                date = modificationDate,
                benefitAmount = benefitAmount,
            )
        }
    }
}

@Composable
private fun VehicleImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(Color(0xFFD9D9D9))
    )
}

@Composable
private fun VehicleMainInfo(
    makeModel: String,
    plateNumber: String,
    ownerName: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(12.dp)) {
        VehicleBasicInfo(
            makeModel = makeModel,
            plateNumber = plateNumber,
            ownerName = ownerName,
        )
        StaffRow()
    }
}

@Composable
private fun VehicleBasicInfo(
    makeModel: String,
    plateNumber: String,
    ownerName: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = makeModel,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = plateNumber,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium,
        )
        Text(
            text = ownerName,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Composable
private fun StaffRow(modifier: Modifier = Modifier) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        repeat(3) {
            Box(
                modifier = modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFD9D9D9))
            )
        }
    }
}

@Composable
private fun BudgetedExtraInfo(
    isPending: Boolean,
    date: String,
    benefitAmount: Double,
    modifier: Modifier = Modifier,
) {
    val tagText = if (isPending) {
        stringResource(R.string.vehicle_pending_tag)
    } else {
        stringResource(R.string.vehicle_accepted_tag)
    }
    val tagColor = if (isPending) {
        Color(0xFFD28E08)
    } else {
        Color(0xFF0CBCBC)
    }
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Tag(
            text = tagText,
            color = tagColor,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        Text(
            text = date,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelMedium,
        )
        BenefitAmount(
            amount = benefitAmount,
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}

@Composable
private fun OtherExtraInfo(
    referenceNumber: String,
    date: String,
    benefitAmount: Double,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.End) {
        Text(
            text = stringResource(R.string.reference_number, referenceNumber),
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = date,
            color = MaterialTheme.colorScheme.tertiary,
            style = MaterialTheme.typography.labelMedium,
        )
        BenefitAmount(
            amount = benefitAmount,
            modifier = Modifier.padding(top = 30.dp)
        )
    }
}

@Composable
private fun BenefitAmount(
    amount: Double,
    modifier: Modifier = Modifier,
) {
    val color = if (amount > 0) {
        Color(0xFF447740)
    } else {
        Color(0xFFAE372F)
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = if (amount > 0) {
                Icons.Default.North
            } else {
                Icons.Default.South
            },
            contentDescription = null,
            tint = color,
        )
        Text(
            text = amount.toEuroCurrency(),
            color = color,
            style = MaterialTheme.typography.titleMedium,
        )
    }
}
