package com.transigo.app;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0016\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a\b\u0010\u0004\u001a\u00020\u0001H\u0003\u001a8\u0010\u0005\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\rH\u0003\u001a\b\u0010\u000e\u001a\u00020\u0001H\u0003\u001a\u001a\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0007\u001a \u0010\u0012\u001a\u00020\u00012\b\u0010\b\u001a\u0004\u0018\u00010\t2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001aB\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u0019\u001a\u00020\u001a2\b\b\u0002\u0010\u001b\u001a\u00020\u001a2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a\b\u0010\u001c\u001a\u00020\u0001H\u0003\u001aB\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\u0016\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u00182\b\b\u0002\u0010\u001e\u001a\u00020\u001f2\b\b\u0002\u0010\u001b\u001a\u00020\u001a2\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a5\u0010 \u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\u000b2\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\"\u001a\u00020#H\u0003\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b$\u0010%\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006&"}, d2 = {"AdminFeatureCard", "", "onClick", "Lkotlin/Function0;", "BackgroundElements", "FeatureCardsSection", "navController", "Landroidx/navigation/NavController;", "user", "Lcom/transigo/app/data/model/User;", "selectedFeature", "", "onFeatureSelect", "Lkotlin/Function1;", "HeroSection", "HomeScreen", "authViewModel", "Lcom/transigo/app/auth/AuthViewModel;", "ModernHeader", "onLogout", "PremiumFeatureCard", "title", "subtitle", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "isPrimary", "", "isSelected", "QuickStatsSection", "SecondaryFeatureCard", "modifier", "Landroidx/compose/ui/Modifier;", "StatCard", "value", "color", "Landroidx/compose/ui/graphics/Color;", "StatCard-g2O1Hgs", "(Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;J)V", "app_debug"})
public final class HomeScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void HomeScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.transigo.app.auth.AuthViewModel authViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void BackgroundElements() {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ModernHeader(com.transigo.app.data.model.User user, kotlin.jvm.functions.Function0<kotlin.Unit> onLogout) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void HeroSection() {
    }
    
    @androidx.compose.runtime.Composable
    private static final void QuickStatsSection() {
    }
    
    @androidx.compose.runtime.Composable
    private static final void FeatureCardsSection(androidx.navigation.NavController navController, com.transigo.app.data.model.User user, java.lang.String selectedFeature, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onFeatureSelect) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void PremiumFeatureCard(java.lang.String title, java.lang.String subtitle, androidx.compose.ui.graphics.vector.ImageVector icon, boolean isPrimary, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void SecondaryFeatureCard(java.lang.String title, java.lang.String subtitle, androidx.compose.ui.graphics.vector.ImageVector icon, androidx.compose.ui.Modifier modifier, boolean isSelected, kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void AdminFeatureCard(kotlin.jvm.functions.Function0<kotlin.Unit> onClick) {
    }
}