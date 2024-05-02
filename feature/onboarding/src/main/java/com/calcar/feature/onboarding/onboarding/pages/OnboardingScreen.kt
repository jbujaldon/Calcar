package com.calcar.feature.onboarding.onboarding.pages

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.calcar.common.domain.entities.StaffId
import com.calcar.feature.onboarding.R
import com.calcar.feature.onboarding.ui.models.StaffIdUi
import com.calcar.feature.onboarding.ui.models.StaffUi
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun OnboardingScreen(viewModel: OnboardingViewModel = koinViewModel()) {
    val isPreviousButtonVisible by viewModel.isPreviousButtonVisible.collectAsStateWithLifecycle()
    val isNextButtonEnabled by viewModel.isNextButtonEnabled.collectAsStateWithLifecycle()
    val currentPage by viewModel.currentPage.collectAsStateWithLifecycle()
    val staffList by viewModel.staffList.collectAsStateWithLifecycle()

    OnboardingContent(
        currentPage = currentPage,
        staffList = staffList,
        isPreviousButtonVisible = isPreviousButtonVisible,
        isNextButtonEnabled = isNextButtonEnabled,
        onPreviousContent = viewModel::onPreviousPage,
        onNextContent = viewModel::onNextPage,
        onAddStaff = viewModel::onAddStaff,
        onDeleteStaff = viewModel::onDeleteStaff,
    )
}

@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
private fun OnboardingContent(
    currentPage: OnboardingPageUi,
    staffList: List<StaffUi>,
    isPreviousButtonVisible: Boolean,
    isNextButtonEnabled: Boolean,
    onPreviousContent: () -> Unit,
    onNextContent: () -> Unit,
    onAddStaff: () -> Unit,
    onDeleteStaff: (StaffIdUi) -> Unit,
) {
    val transition = updateTransition(targetState = currentPage, label = "")
    val progress by transition.animateFloat(label = "") { page ->
        (page.ordinal + 1) / OnboardingPageUi.entries.size.toFloat()
    }

    Scaffold(
        topBar = {
            TopProgressBar(
                progress = progress,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 32.dp)
            )
        },
        bottomBar = {
            BottomNavigationBar(
                isPreviousButtonVisible = isPreviousButtonVisible,
                isNextButtonEnabled = isNextButtonEnabled,
                onPreviousPage = onPreviousContent,
                onNextPage = onNextContent,
            )
        },
    ) { innerPadding ->
        transition.AnimatedContent(
            transitionSpec = { slideTransition() },
        ) { targetPage ->
            when (targetPage) {
                OnboardingPageUi.Staff -> OnboardingStaffContent(
                    page = currentPage,
                    staffList = staffList,
                    onClickAddStaff = onAddStaff,
                    onClickDeleteStaff = onDeleteStaff,
                    modifier = Modifier.padding(innerPadding),
                )
                OnboardingPageUi.SemiFixExpenses -> OnboardingSemiFixExpensesContent(
                    page = currentPage,
                    onClickAddSemiFixExpense = { /*TODO*/ },
                    modifier = Modifier.padding(innerPadding),
                )
                else -> {}
            }
        }
    }
}

@Composable
private fun TopProgressBar(progress: Float, modifier: Modifier = Modifier) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier.fillMaxWidth(),
    )
}

@Composable
private fun BottomNavigationBar(
    isPreviousButtonVisible: Boolean,
    isNextButtonEnabled: Boolean,
    onPreviousPage: () -> Unit,
    onNextPage: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surfaceContainerLow,
        shadowElevation = 16.dp,
    ) {
        BottomNavigationLayout(
            isPreviousButtonVisible = isPreviousButtonVisible,
            isNextButtonEnabled = isNextButtonEnabled,
            onClickPrevious = onPreviousPage,
            onClickNext = onNextPage,
        )
    }
}

@Composable
private fun BottomNavigationLayout(
    isPreviousButtonVisible: Boolean,
    isNextButtonEnabled: Boolean,
    onClickPrevious: () -> Unit,
    onClickNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        AnimatedPreviousButton(
            isPreviousButtonVisible = isPreviousButtonVisible,
            onClickPrevious = onClickPrevious,
        )
        NextButton(
            onClick = onClickNext,
            enabled = isNextButtonEnabled,
            modifier = Modifier.align(Alignment.CenterEnd),
        )
    }
}

@Composable
private fun BoxScope.AnimatedPreviousButton(
    isPreviousButtonVisible: Boolean,
    onClickPrevious: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = isPreviousButtonVisible,
        modifier = modifier,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        PreviousButton(
            onClick = onClickPrevious,
            modifier = Modifier.align(Alignment.CenterStart),
        )
    }
}

@Composable
private fun PreviousButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = stringResource(R.string.previous_button))
    }
}

@Composable
private fun NextButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
    ) {
        Text(text = stringResource(R.string.next_button))
    }
}

private fun getTransitionDirection(
    initialIndex: Int,
    targetIndex: Int,
): AnimatedContentTransitionScope.SlideDirection =
    if (targetIndex > initialIndex) {
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        AnimatedContentTransitionScope.SlideDirection.Right
    }

private fun AnimatedContentTransitionScope<OnboardingPageUi>.slideTransition(): ContentTransform {
    val animationSpec: TweenSpec<IntOffset> = tween(300)
    val direction = getTransitionDirection(
        initialIndex = initialState.ordinal,
        targetIndex = targetState.ordinal,
    )
    return slideIntoContainer(
        towards = direction,
        animationSpec = animationSpec,
    ) togetherWith slideOutOfContainer(
        towards = direction,
        animationSpec = animationSpec,
    )
}
