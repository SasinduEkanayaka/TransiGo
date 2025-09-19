package com.transigo.app.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
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
import kotlin.math.absoluteValue

private data class Page(
    val image: Int, 
    val title: Int, 
    val desc: Int, 
    val backgroundDrawable: Int
)

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun OnboardingScreen(navController: NavHostController, viewModel: OnboardingViewModel) {
    val pages = listOf(
        Page(R.drawable.ic_auth_banner, R.string.onb_title_1, R.string.onb_desc_1, R.drawable.on1),
        Page(R.drawable.ic_booking_ticket, R.string.onb_title_2, R.string.onb_desc_2, R.drawable.on2),
        Page(R.drawable.ic_route, R.string.onb_title_3, R.string.onb_desc_3, R.drawable.on3)
    )

    val pagerState = rememberPagerState(initialPage = 0)
    val scope = rememberCoroutineScope()
    
    // Animated values for smooth transitions
    val density = LocalDensity.current
    val pageOffset = pagerState.currentPageOffsetFraction
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HorizontalPager(
            pageCount = pages.size, 
            state = pagerState, 
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            val page = pages[pageIndex]
            
            // Calculate parallax effect
            val offset = (pagerState.currentPage - pageIndex) + pagerState.currentPageOffsetFraction
            val scale = lerp(1f, 0.85f, offset.absoluteValue.coerceAtMost(1f))
            val alpha = lerp(1f, 0.3f, offset.absoluteValue.coerceAtMost(1f))
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        this.alpha = alpha
                    }
            ) {
                // Background with drawable
                Image(
                    painter = painterResource(id = page.backgroundDrawable),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                
                // Content overlay with glass morphism
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 0.3f),
                                    Color.Black.copy(alpha = 0.7f)
                                ),
                                startY = 0f,
                                endY = Float.POSITIVE_INFINITY
                            )
                        )
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(120.dp))
                    
                    // Hero Image with floating animation (hidden for page 3)
                    if (pageIndex != 2) { // Hide image for page 3 (index 2)
                        val infiniteTransition = rememberInfiniteTransition(label = "floating")
                        val floatingOffset by infiniteTransition.animateFloat(
                            initialValue = 0f,
                            targetValue = 20f,
                            animationSpec = infiniteRepeatable(
                                animation = tween(3000, easing = EaseInOutSine),
                                repeatMode = RepeatMode.Reverse
                            ),
                            label = "floating"
                        )
                        
                        Card(
                            modifier = Modifier
                                .size(280.dp)
                                .offset(y = floatingOffset.dp)
                                .clip(RoundedCornerShape(32.dp)),
                            elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White.copy(alpha = 0.15f)
                            )
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(
                                        brush = Brush.radialGradient(
                                            colors = listOf(
                                                Color.White.copy(alpha = 0.3f),
                                                Color.White.copy(alpha = 0.1f)
                                            )
                                        )
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = page.image),
                                    contentDescription = null,
                                    contentScale = ContentScale.Fit,
                                    modifier = Modifier
                                        .size(200.dp)
                                        .padding(24.dp)
                                )
                            }
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(48.dp))
                    
                    // Content Card with glass morphism
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.15f)
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.White.copy(alpha = 0.2f),
                                            Color.White.copy(alpha = 0.05f)
                                        )
                                    )
                                )
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = stringResource(id = page.title),
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    fontSize = 32.sp,
                                    letterSpacing = (-0.5).sp
                                ),
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            Text(
                                text = stringResource(id = page.desc),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 18.sp,
                                    lineHeight = 26.sp
                                ),
                                color = Color.White.copy(alpha = 0.9f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
        
        // Modern Page Indicators
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 140.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(pages.size) { index ->
                val selected = pagerState.currentPage == index
                val animatedWidth by animateDpAsState(
                    targetValue = if (selected) 40.dp else 12.dp,
                    animationSpec = tween(300, easing = EaseOutCubic),
                    label = "indicator_width"
                )
                val animatedAlpha by animateFloatAsState(
                    targetValue = if (selected) 1f else 0.5f,
                    animationSpec = tween(300),
                    label = "indicator_alpha"
                )
                
                Box(
                    modifier = Modifier
                        .width(animatedWidth)
                        .height(12.dp)
                        .alpha(animatedAlpha)
                        .clip(RoundedCornerShape(6.dp))
                        .background(
                            brush = if (selected) {
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.White,
                                        Color.White.copy(alpha = 0.8f)
                                    )
                                )
                            } else {
                                Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.White.copy(alpha = 0.5f),
                                        Color.White.copy(alpha = 0.3f)
                                    )
                                )
                            }
                        )
                )
            }
        }
        
        // Navigation Controls
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val isLast = pagerState.currentPage == pages.lastIndex
            
            // Skip Button
            AnimatedVisibility(
                visible = !isLast,
                enter = fadeIn() + slideInHorizontally { -it },
                exit = fadeOut() + slideOutHorizontally { -it }
            ) {
                TextButton(
                    onClick = {
                        viewModel.markCompleted()
                        navController.navigate(NavigationRoutes.AUTH) {
                            popUpTo(NavigationRoutes.ONBOARDING) { inclusive = true }
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.skip),
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            // Next/Get Started Button
            Card(
                modifier = Modifier,
                shape = RoundedCornerShape(30.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.9f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
            ) {
                FilledTonalButton(
                    onClick = {
                        if (isLast) {
                            viewModel.markCompleted()
                            navController.navigate(NavigationRoutes.AUTH) {
                                popUpTo(NavigationRoutes.ONBOARDING) { inclusive = true }
                            }
                        } else {
                            scope.launch { 
                                pagerState.animateScrollToPage(
                                    pagerState.currentPage + 1,
                                    animationSpec = tween(500, easing = EaseOutCubic)
                                ) 
                            }
                        }
                    },
                    modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.filledTonalButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.primary
                    ),
                    contentPadding = PaddingValues(horizontal = 32.dp, vertical = 16.dp)
                ) {
                    Text(
                        text = if (isLast) stringResource(id = R.string.get_started) 
                               else stringResource(id = R.string.next),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    
                    if (!isLast) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}

// Helper function for linear interpolation
private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}
