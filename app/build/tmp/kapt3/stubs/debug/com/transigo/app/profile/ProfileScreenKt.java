package com.transigo.app.profile;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000Z\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a8\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\u0018\u0010\t\u001a\u0014\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001aG\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0010\u001a\u00020\u00112\b\b\u0002\u0010\u0012\u001a\u00020\u0013H\u0007\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0014\u0010\u0015\u001a<\u0010\u0016\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u00112\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00030\u0019H\u0007\u001a.\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u001c2\b\b\u0002\u0010\u001d\u001a\u00020\u001e2\b\b\u0002\u0010\u001f\u001a\u00020 2\b\b\u0002\u0010!\u001a\u00020\"H\u0007\u0082\u0002\u000b\n\u0005\b\u00a1\u001e0\u0001\n\u0002\b\u0019\u00a8\u0006#"}, d2 = {"CardDropdownItem", "", "card", "Lcom/transigo/app/data/model/SavedCard;", "EditProfileDialog", "currentName", "", "onDismiss", "Lkotlin/Function0;", "onSave", "Lkotlin/Function2;", "ProfileMenuItem", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "title", "onClick", "showArrow", "", "textColor", "Landroidx/compose/ui/graphics/Color;", "ProfileMenuItem-xwkQ0AY", "(Landroidx/compose/ui/graphics/vector/ImageVector;Ljava/lang/String;Lkotlin/jvm/functions/Function0;ZJ)V", "ProfileMenuItemWithDropdown", "isExpanded", "savedCards", "", "ProfileScreen", "navController", "Landroidx/navigation/NavController;", "profileViewModel", "Lcom/transigo/app/profile/ProfileViewModel;", "authViewModel", "Lcom/transigo/app/auth/AuthViewModel;", "bookingViewModel", "Lcom/transigo/app/booking/BookingViewModel;", "app_debug"})
public final class ProfileScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void ProfileScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.transigo.app.profile.ProfileViewModel profileViewModel, @org.jetbrains.annotations.NotNull
    com.transigo.app.auth.AuthViewModel authViewModel, @org.jetbrains.annotations.NotNull
    com.transigo.app.booking.BookingViewModel bookingViewModel) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void ProfileMenuItemWithDropdown(@org.jetbrains.annotations.NotNull
    androidx.compose.ui.graphics.vector.ImageVector icon, @org.jetbrains.annotations.NotNull
    java.lang.String title, boolean isExpanded, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onClick, @org.jetbrains.annotations.NotNull
    java.util.List<com.transigo.app.data.model.SavedCard> savedCards) {
    }
    
    @androidx.compose.runtime.Composable
    public static final void CardDropdownItem(@org.jetbrains.annotations.NotNull
    com.transigo.app.data.model.SavedCard card) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable
    public static final void EditProfileDialog(@org.jetbrains.annotations.NotNull
    java.lang.String currentName, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, @org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function2<? super java.lang.String, ? super java.lang.String, kotlin.Unit> onSave) {
    }
}