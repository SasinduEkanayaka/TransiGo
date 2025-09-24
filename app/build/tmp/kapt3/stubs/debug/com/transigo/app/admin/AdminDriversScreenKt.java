package com.transigo.app.admin;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000F\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a$\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u001a>\u0010\t\u001a\u00020\u00012\b\u0010\n\u001a\u0004\u0018\u00010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\u000f2\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007\u001a \u0010\u0010\u001a\u00020\u00012\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007\u001a6\u0010\u0012\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u0005\u001a\u00020\u0006H\u0007\u001a\u0012\u0010\u0015\u001a\u00020\u00162\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0002\u001a\u0010\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u0016H\u0002\u001a\u0010\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001c\u001a\u00020\u001dH\u0002\u00a8\u0006\u001e"}, d2 = {"AdminDriversScreen", "", "navController", "Landroidx/navigation/NavController;", "AdminDriversScreenContent", "modifier", "Landroidx/compose/ui/Modifier;", "viewModel", "Lcom/transigo/app/admin/DriverViewModel;", "DriverDialog", "driver", "Lcom/transigo/app/data/model/Driver;", "onDismiss", "Lkotlin/Function0;", "onConfirm", "Lkotlin/Function1;", "EmptyDriversState", "onAddClick", "ModernDriverCard", "onEditClick", "onDeleteClick", "formatDriverDate", "", "timestamp", "Lcom/google/firebase/Timestamp;", "generateVehicleId", "driverId", "getVehiclePrefix", "vehicleType", "Lcom/transigo/app/data/model/VehicleType;", "app_debug"})
public final class AdminDriversScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void AdminDriversScreenContent(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier, @org.jetbrains.annotations.NotNull
    com.transigo.app.admin.DriverViewModel viewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void AdminDriversScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ModernDriverCard(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.Driver driver, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onEditClick, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDeleteClick, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void EmptyDriversState(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onAddClick, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
    
    private static final java.lang.String formatDriverDate(com.google.firebase.Timestamp timestamp) {
        return null;
    }
    
    private static final java.lang.String getVehiclePrefix(com.transigo.app.data.model.VehicleType vehicleType) {
        return null;
    }
    
    private static final java.lang.String generateVehicleId(java.lang.String driverId) {
        return null;
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void DriverDialog(@org.jetbrains.annotations.Nullable
    com.transigo.app.data.model.Driver driver, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super com.transigo.app.data.model.Driver, kotlin.Unit> onConfirm, @org.jetbrains.annotations.NotNull
    androidx.compose.ui.Modifier modifier) {
    }
}