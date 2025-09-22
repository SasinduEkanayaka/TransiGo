package com.transigo.app.utils;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, xi = 48, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u0004J\u000e\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u0004\u00a8\u0006\u000e"}, d2 = {"Lcom/transigo/app/utils/DistanceUtils;", "", "()V", "calculateDistance", "", "lat1", "lon1", "lat2", "lon2", "formatDistance", "", "distance", "formatPrice", "price", "app_debug"})
public final class DistanceUtils {
    @org.jetbrains.annotations.NotNull
    public static final com.transigo.app.utils.DistanceUtils INSTANCE = null;
    
    private DistanceUtils() {
        super();
    }
    
    /**
     * Calculate distance between two points using Haversine formula
     * @param lat1 Latitude of first point
     * @param lon1 Longitude of first point
     * @param lat2 Latitude of second point
     * @param lon2 Longitude of second point
     * @return Distance in kilometers
     */
    public final double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        return 0.0;
    }
    
    /**
     * Format distance for display
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String formatDistance(double distance) {
        return null;
    }
    
    /**
     * Format price for display
     */
    @org.jetbrains.annotations.NotNull
    public final java.lang.String formatPrice(double price) {
        return null;
    }
}