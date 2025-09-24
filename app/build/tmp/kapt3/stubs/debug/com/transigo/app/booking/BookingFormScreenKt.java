package com.transigo.app.booking;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a$\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u000bH\u0007\u001a \u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u00032\u0006\u0010\u0010\u001a\u00020\u0003H\u0002\u001a \u0010\u0011\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\tH\u0002\u001a \u0010\u0016\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\tH\u0002\u001a \u0010\u0018\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001aH\u0002\"\u001a\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001c"}, d2 = {"sriLankanLocations", "", "", "Lorg/osmdroid/util/GeoPoint;", "BookingFormScreen", "", "navController", "Landroidx/navigation/NavController;", "bookingViewModel", "Lcom/transigo/app/booking/BookingViewModel;", "authViewModel", "Lcom/transigo/app/auth/AuthViewModel;", "drawStraightLineRoute", "mapView", "Lorg/osmdroid/views/MapView;", "fromPoint", "toPoint", "searchLocationInSriLanka", "query", "isFromLocation", "", "viewModel", "setLocationWithCoordinates", "address", "updateMapMarkers", "fromLocation", "Lcom/transigo/app/data/model/Location;", "toLocation", "app_debug"})
public final class BookingFormScreenKt {
    @org.jetbrains.annotations.NotNull
    private static final java.util.Map<java.lang.String, org.osmdroid.util.GeoPoint> sriLankanLocations = null;
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, com.google.accompanist.permissions.ExperimentalPermissionsApi.class})
    @androidx.compose.runtime.Composable
    public static final void BookingFormScreen(@org.jetbrains.annotations.NotNull
    androidx.navigation.NavController navController, @org.jetbrains.annotations.NotNull
    com.transigo.app.booking.BookingViewModel bookingViewModel, @org.jetbrains.annotations.NotNull
    com.transigo.app.auth.AuthViewModel authViewModel) {
    }
    
    private static final void updateMapMarkers(org.osmdroid.views.MapView mapView, com.transigo.app.data.model.Location fromLocation, com.transigo.app.data.model.Location toLocation) {
    }
    
    private static final void drawStraightLineRoute(org.osmdroid.views.MapView mapView, org.osmdroid.util.GeoPoint fromPoint, org.osmdroid.util.GeoPoint toPoint) {
    }
    
    private static final void setLocationWithCoordinates(java.lang.String address, boolean isFromLocation, com.transigo.app.booking.BookingViewModel viewModel) {
    }
    
    private static final void searchLocationInSriLanka(java.lang.String query, boolean isFromLocation, com.transigo.app.booking.BookingViewModel viewModel) {
    }
}