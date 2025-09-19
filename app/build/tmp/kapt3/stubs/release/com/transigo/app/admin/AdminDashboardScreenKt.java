package com.transigo.app.admin;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007H\u0007\u001aJ\u0010\b\u001a\u00020\u00012\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\n2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\u0006\u0010\u0010\u001a\u00020\u0011H\u0003\u001a\b\u0010\u0012\u001a\u00020\u0001H\u0003\u001a(\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00152\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0003\u001a\u0018\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u001bH\u0003\u001a\u001a\u0010\u001c\u001a\u00020\u00012\u0006\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0003\u001a\u0018\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020!2\u0006\u0010\u001a\u001a\u00020\u001bH\u0003\u001a\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u001e0#2\u0006\u0010 \u001a\u00020!H\u0002\u001a\u000e\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00150#H\u0002\u00a8\u0006%"}, d2 = {"AdminDashboardScreen", "", "navController", "Landroidx/navigation/NavController;", "authViewModel", "Lcom/transigo/app/auth/AuthViewModel;", "dashboardViewModel", "Lcom/transigo/app/admin/AdminDashboardViewModel;", "ModernAdminHeader", "title", "", "subtitle", "onBack", "Lkotlin/Function0;", "onRefresh", "onLogout", "isLoading", "", "ModernLoadingCard", "ModernQuickActionCard", "action", "Lcom/transigo/app/admin/QuickAction;", "onClick", "modifier", "Landroidx/compose/ui/Modifier;", "ModernQuickActionsSection", "screenWidth", "", "SuperbKpiCard", "kpi", "Lcom/transigo/app/admin/KpiItem;", "SuperbKpiDashboard", "stats", "Lcom/transigo/app/data/model/DashboardStats;", "getEnhancedKpiItems", "", "getQuickActionItems", "app_release"})
public final class AdminDashboardScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AdminDashboardScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.transigo.app.auth.AuthViewModel authViewModel, @org.jetbrains.annotations.NotNull
    com.transigo.app.admin.AdminDashboardViewModel dashboardViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ModernAdminHeader(java.lang.String title, java.lang.String subtitle, kotlin.jvm.functions.Function0<kotlin.Unit> onBack, kotlin.jvm.functions.Function0<kotlin.Unit> onRefresh, kotlin.jvm.functions.Function0<kotlin.Unit> onLogout, boolean isLoading) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ModernLoadingCard() {
    }
    
    @androidx.compose.runtime.Composable
    private static final void SuperbKpiDashboard(com.transigo.app.data.model.DashboardStats stats, int screenWidth) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void SuperbKpiCard(com.transigo.app.admin.KpiItem kpi, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ModernQuickActionsSection(androidx.navigation.NavController navController, int screenWidth) {
    }
    
    @androidx.compose.runtime.Composable
    private static final void ModernQuickActionCard(com.transigo.app.admin.QuickAction action, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.util.List<com.transigo.app.admin.KpiItem> getEnhancedKpiItems(com.transigo.app.data.model.DashboardStats stats) {
        return null;
    }
    
    private static final java.util.List<com.transigo.app.admin.QuickAction> getQuickActionItems() {
        return null;
    }
}