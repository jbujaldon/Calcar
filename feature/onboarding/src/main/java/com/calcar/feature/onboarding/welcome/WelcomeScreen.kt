package com.calcar.feature.onboarding.welcome

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.calcar.common.ui.navigation.NoArgDestination
import com.calcar.feature.onboarding.R
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun WelcomeScreen(viewModel: WelcomeViewModel = koinViewModel()) {
    WelcomeContent {
        viewModel.onStart()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun WelcomeContent(onClickStart: () -> Unit) {
    val pager = rememberPagerState { CarouselInformation.entries.size }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Carousel(
                pager = pager,
                modifier = Modifier.padding(bottom = 36.dp),
            )
            CarouselIndicator(
                currentIndex = pager.currentPage,
                maxIndex = pager.pageCount,
                modifier = Modifier.padding(bottom = 40.dp),
            )
            StartButton(
                onClick = onClickStart,
                modifier = Modifier.padding(bottom = 56.dp)
            )
        }
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun ColumnScope.Carousel(pager: PagerState, modifier: Modifier = Modifier) {
    HorizontalPager(
        state = pager,
        modifier = modifier.weight(1f)
    ) { pageIndex ->
        CarouselContent(information = CarouselInformation.entries[pageIndex])
    }
}

@Composable
private fun CarouselContent(information: CarouselInformation, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CarouselImage(information.image)
        CarouselDescription(information.title, information.description)
    }
}

@Composable
private fun ColumnScope.CarouselImage(
    imageRes: Int,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(imageRes),
        contentDescription = null,
        alignment = Alignment.BottomCenter,
        modifier = modifier
            .weight(1f)
            .padding(bottom = 32.dp),
    )
}

@Composable
private fun CarouselDescription(titleRes: Int, descriptionRes: Int) {
    Text(
        text = stringResource(titleRes),
        modifier = Modifier.padding(bottom = 16.dp),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.headlineMedium,
    )
    Text(
        text = stringResource(descriptionRes),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
private fun CarouselIndicator(
    currentIndex: Int,
    maxIndex: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        repeat(maxIndex) { index ->
            val indicatorColor = if (index == currentIndex) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.outlineVariant
            }
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(indicatorColor)
                    .size(8.dp),
            )
        }
    }
}

@Composable
private fun StartButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Text(text = stringResource(R.string.welcome_start_button))
    }
}