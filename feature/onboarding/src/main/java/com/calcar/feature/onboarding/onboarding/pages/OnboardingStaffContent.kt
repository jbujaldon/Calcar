package com.calcar.feature.onboarding.onboarding.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.calcar.common.ui.utils.toEuroCurrency
import com.calcar.common.core.wrappers.toCompose
import com.calcar.common.ui.components.Tag
import com.calcar.feature.onboarding.R
import com.calcar.feature.onboarding.ui.components.AddButton
import com.calcar.feature.onboarding.ui.components.EmptyContent
import com.calcar.feature.onboarding.ui.components.PageScaffold
import com.calcar.common.ui.models.StaffIdUi
import com.calcar.common.ui.models.StaffUi
import com.calcar.common.ui.models.ProfessionUi

@Composable
internal fun OnboardingStaffContent(
    page: OnboardingPageUi,
    staffList: List<StaffUi>,
    onClickAddStaff: () -> Unit,
    onClickDeleteStaff: (StaffIdUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.fillMaxSize()) {
        PageContent(
            page = page,
            staffList = staffList,
            onClickDeleteStaff = onClickDeleteStaff,
        )
        AddButton(
            text = stringResource(R.string.add_staff_button),
            onClick = onClickAddStaff,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        )
    }
}

@Composable
private fun PageContent(
    page: OnboardingPageUi,
    staffList: List<StaffUi>,
    onClickDeleteStaff: (StaffIdUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    PageScaffold(page = page, modifier = modifier) {
        if (staffList.isEmpty()) {
            EmptyContent(
                imageRes = painterResource(id = R.drawable.empty_staff),
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 76.dp)
            )
        } else {
            StaffList(
                staffList = staffList,
                onClickRemove = onClickDeleteStaff,
            )
        }
    }
}

@Composable
private fun StaffList(
    staffList: List<StaffUi>,
    onClickRemove: (StaffIdUi) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(staffList) { staff ->
            StaffItem(
                staff = staff,
                onClickRemove = { onClickRemove(staff.id) }
            )
            HorizontalDivider(modifier = Modifier.padding(start = 24.dp))
        }
    }
}

@Composable
private fun StaffItem(
    staff: StaffUi,
    onClickRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(start = 24.dp, end = 8.dp)
            .padding(vertical = 16.dp),
    ) {
        StaffImage(modifier = Modifier.padding(end = 20.dp))
        StaffInfo(
            fullName = staff.fullName,
            salary = staff.salary,
            profession = staff.profession,
            modifier = Modifier.weight(1f),
        )
        RemoveIcon(
            onClick = onClickRemove,
            modifier = Modifier.align(Alignment.CenterVertically),
        )
    }
}

@Composable
private fun StaffImage(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(Color(0xFFD9D9D9))
    )
}

@Composable
private fun StaffInfo(
    fullName: String,
    salary: Double,
    profession: ProfessionUi,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = fullName,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = salary.toEuroCurrency(),
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium,
        )
        Tag(
            text = stringResource(profession.label),
            color = profession.color.toCompose()
        )
    }
}

@Composable
private fun RemoveIcon(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = Icons.Default.Close,
            contentDescription = null,
        )
    }
}
