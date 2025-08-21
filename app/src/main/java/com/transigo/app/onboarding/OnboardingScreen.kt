package com.transigo.app.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.TextButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.transigo.app.core.navigation.NavigationRoutes
import com.transigo.app.R
import kotlinx.coroutines.launch

private data class Page(val image: Int, val title: Int, val desc: Int, val bg: Color)

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun OnboardingScreen(navController: NavHostController, viewModel: OnboardingViewModel) {
    val pages = listOf(
        Page(R.drawable.ic_auth_banner, R.string.onb_title_1, R.string.onb_desc_1, Color(0xFF0EA5E9)),
        Page(R.drawable.ic_booking_ticket, R.string.onb_title_2, R.string.onb_desc_2, Color(0xFF16A34A)),
        Page(R.drawable.ic_route, R.string.onb_title_3, R.string.onb_desc_3, Color(0xFF9333EA))
    )

    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(Modifier.fillMaxSize()) {
            HorizontalPager(pageCount = pages.size, state = pagerState, modifier = Modifier.weight(1f)) { page ->
                val p = pages[page]
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(p.bg)
                        .padding(24.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = p.image),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(220.dp)
                        )
                        Spacer(Modifier.height(32.dp))
                        Text(
                            text = stringResource(id = p.title),
                            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color.White,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            text = stringResource(id = p.desc),
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.White.copy(alpha = 0.9f),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Page indicators
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { index ->
                    val selected = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(if (selected) 10.dp else 8.dp)
                            .background(
                                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                shape = MaterialTheme.shapes.large
                            )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val isLast = pagerState.currentPage == pages.lastIndex
                TextButton(onClick = {
                    if (!isLast) {
                        viewModel.markCompleted()
                        navController.navigate(NavigationRoutes.AUTH) {
                            popUpTo(NavigationRoutes.ONBOARDING) { inclusive = true }
                        }
                    }
                }, enabled = !isLast) {
                    if (!isLast) Text(text = stringResource(id = R.string.skip))
                }

                FilledTonalButton(onClick = {
                    if (isLast) {
                        viewModel.markCompleted()
                        navController.navigate(NavigationRoutes.AUTH) {
                            popUpTo(NavigationRoutes.ONBOARDING) { inclusive = true }
                        }
                    } else {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }
                }) {
                    Text(text = if (isLast) stringResource(id = R.string.get_started) else stringResource(id = R.string.next))
                }
            }
        }
    }
}
