package com.transigo.app.onboarding

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.input.pointer.pointerInput
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
import kotlinx.coroutines.delay
import kotlin.math.sin

private data class ModernOnboardingPage(
    val backgroundImage: Int,
    val title: Int,
    val description: Int,
    val accentColor: Color,
    val gradientColors: List<Color>
)

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun OnboardingScreen(navController: NavHostController, viewModel: OnboardingViewModel) {
    val pages = listOf(
        ModernOnboardingPage(
            backgroundImage = R.drawable.on1, 
            title = R.string.onb_title_1, 
            description = R.string.onb_desc_1,
            accentColor = Color(0xFF00D4AA),
            gradientColors = listOf(
                Color(0xFF1A1A2E).copy(alpha = 0.7f),
                Color(0xFF16213E).copy(alpha = 0.85f),
                Color.Black.copy(alpha = 0.9f)
            )
        ),
        ModernOnboardingPage(
            backgroundImage = R.drawable.on2, 
            title = R.string.onb_title_2, 
            description = R.string.onb_desc_2,
            accentColor = Color(0xFF00BCD4),
            gradientColors = listOf(
                Color(0xFF0F4C75).copy(alpha = 0.7f),
                Color(0xFF3282B8).copy(alpha = 0.85f),
                Color.Black.copy(alpha = 0.9f)
            )
        ),
        ModernOnboardingPage(
            backgroundImage = R.drawable.on3, 
            title = R.string.onb_title_3, 
            description = R.string.onb_desc_3,
            accentColor = Color(0xFFFF6B35),
            gradientColors = listOf(
                Color(0xFF2C1810).copy(alpha = 0.7f),
                Color(0xFF8B4513).copy(alpha = 0.85f),
                Color.Black.copy(alpha = 0.9f)
            )
        )
    )

    val pagerState = rememberPagerState(initialPage = 0) { pages.size }
    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }
    
    // Animate entrance
    LaunchedEffect(Unit) {
        delay(300)
        isVisible = true
    }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState, 
            modifier = Modifier.fillMaxSize()
        ) { pageIndex ->
            ModernOnboardingPageContent(
                page = pages[pageIndex],
                isVisible = isVisible,
                pageIndex = pageIndex
            )
        }
        
        // Modern Skip Button
        AnimatedVisibility(
            visible = isVisible && pagerState.currentPage < pages.lastIndex,
            enter = fadeIn(tween(500, delayMillis = 400)),
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(24.dp)
        ) {
            ModernSkipButton(
                onClick = {
                    viewModel.markCompleted()
                    navController.navigate(NavigationRoutes.AUTH) {
                        popUpTo(NavigationRoutes.ONBOARDING) { inclusive = true }
                    }
                }
            )
        }

        // Modern Page Indicators with Glow Effect
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(800, delayMillis = 500)
            ) + fadeIn(tween(800, delayMillis = 500)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 120.dp)
        ) {
            ModernPageIndicators(
                pagerState = pagerState,
                pageCount = pages.size,
                accentColor = pages[pagerState.currentPage].accentColor
            )
        }
        
        // Modern Action Button with Dynamic Colors
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(800, delayMillis = 600)
            ) + fadeIn(tween(800, delayMillis = 600)),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp)
        ) {
            ModernActionButton(
                text = if (pagerState.currentPage == pages.lastIndex) 
                    stringResource(id = R.string.get_started) 
                else 
                    stringResource(id = R.string.next),
                accentColor = pages[pagerState.currentPage].accentColor,
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
                }
            )
        }
    }
}

@Composable
private fun ModernOnboardingPageContent(
    page: ModernOnboardingPage,
    isVisible: Boolean,
    pageIndex: Int
) {
    var imageScale by remember { mutableStateOf(1.1f) }
    val animatedScale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 1.1f,
        animationSpec = tween(1200, delayMillis = pageIndex * 150),
        label = "imageScale"
    )

    LaunchedEffect(isVisible) {
        if (isVisible) {
            while (true) {
                delay(4000)
                imageScale = 1.05f
                delay(4000)
                imageScale = 1f
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Background Image with Enhanced Parallax
        Image(
            painter = painterResource(id = page.backgroundImage),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .scale(animatedScale)
                .blur(radius = 0.3.dp),
            contentScale = ContentScale.Crop
        )

        // Floating Elements Animation
        repeat(3) { index ->
            FloatingElement(
                delay = index * 1000,
                accentColor = page.accentColor.copy(alpha = 0.2f),
                modifier = Modifier
                    .size((16 + index * 6).dp)
                    .offset(
                        x = (60 + index * 80).dp,
                        y = (120 + index * 150).dp
                    )
            )
        }

        // Modern Dynamic Gradient Overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = page.gradientColors,
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // Modern Glassmorphism Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            ModernContentCard(
                page = page,
                isVisible = isVisible,
                pageIndex = pageIndex
            )
            
            Spacer(modifier = Modifier.height(140.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernContentCard(
    page: ModernOnboardingPage,
    isVisible: Boolean,
    pageIndex: Int
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it / 2 },
            animationSpec = tween(800, delayMillis = pageIndex * 100 + 200)
        ) + fadeIn(tween(800, delayMillis = pageIndex * 100 + 200))
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White.copy(alpha = 0.12f)
            ),
            border = BorderStroke(1.dp, Color.White.copy(alpha = 0.25f))
        ) {
            Column(
                modifier = Modifier.padding(28.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // Modern Accent Line with Glow
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(4.dp)
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    page.accentColor,
                                    page.accentColor.copy(alpha = 0.5f)
                                )
                            ),
                            shape = RoundedCornerShape(2.dp)
                        )
                        .shadow(
                            elevation = 8.dp,
                            shape = RoundedCornerShape(2.dp),
                            spotColor = page.accentColor
                        )
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Enhanced Title with Better Typography
                AnimatedText(
                    text = stringResource(id = page.title),
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Black,
                        fontSize = 30.sp,
                        color = Color.White,
                        letterSpacing = (-0.8).sp,
                        lineHeight = 38.sp
                    ),
                    textAlign = TextAlign.Start
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Enhanced Description
                AnimatedText(
                    text = stringResource(id = page.description),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = Color.White.copy(alpha = 0.85f),
                        lineHeight = 26.sp,
                        letterSpacing = 0.2.sp
                    ),
                    textAlign = TextAlign.Start,
                    delay = 300
                )
            }
        }
    }
}

@Composable
private fun AnimatedText(
    text: String,
    style: androidx.compose.ui.text.TextStyle,
    textAlign: TextAlign = TextAlign.Start,
    delay: Int = 0
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(delay.toLong())
        isVisible = true
    }
    
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it / 3 }
        ) + fadeIn(tween(700))
    ) {
        Text(
            text = text,
            style = style,
            textAlign = textAlign,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ModernPageIndicators(
    pagerState: PagerState,
    pageCount: Int,
    accentColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(pageCount) { index ->
            val isSelected = pagerState.currentPage == index
            val animatedWidth by animateDpAsState(
                targetValue = if (isSelected) 36.dp else 12.dp,
                animationSpec = tween(400, easing = FastOutSlowInEasing),
                label = "indicatorWidth"
            )
            val animatedAlpha by animateFloatAsState(
                targetValue = if (isSelected) 1f else 0.4f,
                animationSpec = tween(400),
                label = "indicatorAlpha"
            )
            
            Box(
                modifier = Modifier
                    .width(animatedWidth)
                    .height(8.dp)
                    .background(
                        brush = if (isSelected) {
                            Brush.horizontalGradient(
                                colors = listOf(accentColor, accentColor.copy(alpha = 0.7f))
                            )
                        } else {
                            Brush.horizontalGradient(
                                colors = listOf(
                                    Color.White.copy(alpha = animatedAlpha),
                                    Color.White.copy(alpha = animatedAlpha * 0.5f)
                                )
                            )
                        },
                        shape = RoundedCornerShape(4.dp)
                    )
                    .shadow(
                        elevation = if (isSelected) 6.dp else 0.dp,
                        shape = RoundedCornerShape(4.dp),
                        spotColor = if (isSelected) accentColor else Color.Transparent
                    )
            )
        }
    }
}

@Composable
private fun ModernActionButton(
    text: String,
    accentColor: Color,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "buttonScale"
    )
    
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .scale(scale)
            .pointerInput(Unit) {
                detectTapGestures(
                    onPress = {
                        isPressed = true
                        tryAwaitRelease()
                        isPressed = false
                    }
                )
            },
        shape = RoundedCornerShape(32.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = accentColor
        ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 12.dp,
            pressedElevation = 6.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            letterSpacing = 0.5.sp
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ModernSkipButton(onClick: () -> Unit) {
    Card(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.15f)
        ),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
    ) {
        Text(
            text = stringResource(id = R.string.skip),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun FloatingElement(
    delay: Int,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    var isVisible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(delay.toLong())
        isVisible = true
    }
    
    val infiniteTransition = rememberInfiniteTransition(label = "floating")
    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = -30f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatingY"
    )
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.6f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatingAlpha"
    )
    
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(tween(1200)) + scaleIn(tween(1200))
    ) {
        Box(
            modifier = modifier
                .offset(y = offsetY.dp)
                .background(
                    color = accentColor.copy(alpha = alpha),
                    shape = CircleShape
                )
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    spotColor = accentColor
                )
        )
    }
}
