package com.transigo.app.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.transigo.app.core.navigation.NavigationRoutes
import com.transigo.app.R
import kotlinx.coroutines.launch

private data class OnboardingPage(
    val backgroundImage: Int,
    val title: Int,
    val description: Int
)

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun OnboardingScreen(navController: NavHostController, viewModel: OnboardingViewModel) {
    val pages = listOf(
        OnboardingPage(R.drawable.on1, R.string.onb_title_1, R.string.onb_desc_1),
        OnboardingPage(R.drawable.on2, R.string.onb_title_2, R.string.onb_desc_2),
        OnboardingPage(R.drawable.on3, R.string.onb_title_3, R.string.onb_desc_3)
    )

    val pagerState = rememberPagerState(initialPage = 0) { pages.size }
    val scope = rememberCoroutineScope()
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState, 
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            val page = pages[pageIndex]
            
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                // Background Image
                Image(
                    painter = painterResource(id = page.backgroundImage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                
                // Dark overlay for text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Black.copy(alpha = 0.8f)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                )
                
                // Content at bottom
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 60.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // Title
                    Text(
                        text = stringResource(id = page.title),
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 28.sp,
                            lineHeight = 36.sp
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    // Description
                    Text(
                        text = stringResource(id = page.description),
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 16.sp,
                            lineHeight = 24.sp
                        ),
                        color = Color.White.copy(alpha = 0.9f),
                        textAlign = TextAlign.Left,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(150.dp)) // Space for indicators and button
                }
            }
        }
        
        // Page Indicators - Clean dots like in your images
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 100.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(pages.size) { index ->
                val selected = pagerState.currentPage == index
                val animatedWidth by animateDpAsState(
                    targetValue = if (selected) 24.dp else 8.dp,
                    animationSpec = tween(300),
                    label = "indicator_width"
                )
                
                Box(
                    modifier = Modifier
                        .width(animatedWidth)
                        .height(8.dp)
                        .background(
                            color = if (selected) Color.White else Color.White.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
        
        // Navigation Button - Matches your design
        Button(
            onClick = {
                val isLast = pagerState.currentPage == pages.lastIndex
                if (isLast) {
                    viewModel.markCompleted()
                    navController.navigate(NavigationRoutes.AUTH) {
                        popUpTo(NavigationRoutes.ONBOARDING) { inclusive = true }
                    }
                } else {
                    scope.launch { 
                        pagerState.animateScrollToPage(pagerState.currentPage + 1) 
                    }
                }
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 30.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White.copy(alpha = 0.9f),
                contentColor = Color.Black
            ),
            shape = RoundedCornerShape(28.dp)
        ) {
            Text(
                text = if (pagerState.currentPage == pages.lastIndex) 
                    stringResource(id = R.string.get_started) 
                else 
                    stringResource(id = R.string.next),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}
