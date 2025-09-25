package com.transigo.app.onboarding;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000Z\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a9\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\tH\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\n\u0010\u000b\u001a/\u0010\f\u001a\u00020\u00012\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000e2\b\b\u0002\u0010\u000f\u001a\u00020\u0010H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0011\u0010\u0012\u001a3\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\r\u001a\u00020\u000e2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0015H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0016\u0010\u0017\u001a \u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\tH\u0003\u001a \u0010\u001e\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\tH\u0003\u001a-\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u000eH\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b#\u0010$\u001a\u0016\u0010%\u001a\u00020\u00012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\u0015H\u0003\u001a\u0018\u0010&\u001a\u00020\u00012\u0006\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0007\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006+"}, d2 = {"AnimatedText", "", "text", "", "style", "Landroidx/compose/ui/text/TextStyle;", "textAlign", "Landroidx/compose/ui/text/style/TextAlign;", "delay", "", "AnimatedText-yCmwl5w", "(Ljava/lang/String;Landroidx/compose/ui/text/TextStyle;II)V", "FloatingElement", "accentColor", "Landroidx/compose/ui/graphics/Color;", "modifier", "Landroidx/compose/ui/Modifier;", "FloatingElement-bw27NRU", "(IJLandroidx/compose/ui/Modifier;)V", "ModernActionButton", "onClick", "Lkotlin/Function0;", "ModernActionButton-bw27NRU", "(Ljava/lang/String;JLkotlin/jvm/functions/Function0;)V", "ModernContentCard", "page", "Lcom/transigo/app/onboarding/ModernOnboardingPage;", "isVisible", "", "pageIndex", "ModernOnboardingPageContent", "ModernPageIndicators", "pagerState", "Landroidx/compose/foundation/pager/PagerState;", "pageCount", "ModernPageIndicators-mxwnekA", "(Landroidx/compose/foundation/pager/PagerState;IJ)V", "ModernSkipButton", "OnboardingScreen", "navController", "Landroidx/navigation/NavHostController;", "viewModel", "Lcom/transigo/app/onboarding/OnboardingViewModel;", "app_debug"})
public final class OnboardingScreenKt {
    
    @androidx.compose.runtime.Composable
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.ExperimentalFoundationApi.class})
    public static final void OnboardingScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavHostController navController, @org.jetbrains.annotations.NotNull
    com.transigo.app.onboarding.OnboardingViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ModernOnboardingPageContent(com.transigo.app.onboarding.ModernOnboardingPage page, boolean isVisible, int pageIndex) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void ModernContentCard(com.transigo.app.onboarding.ModernOnboardingPage page, boolean isVisible, int pageIndex) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    private static final void ModernSkipButton(kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}