package com.transigo.app.utils

import kotlin.math.*

object DistanceUtils {
    
    /**
     * Calculate distance between two points using Haversine formula
     * @param lat1 Latitude of first point
     * @param lon1 Longitude of first point
     * @param lat2 Latitude of second point
     * @param lon2 Longitude of second point
     * @return Distance in kilometers
     */
    fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val R = 6371.0 // Earth's radius in kilometers
        
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        
        val a = sin(dLat / 2) * sin(dLat / 2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2) * sin(dLon / 2)
        
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        
        return R * c
    }
    
    /**
     * Format distance for display
     */
    fun formatDistance(distance: Double): String {
        return if (distance < 1.0) {
            "${(distance * 1000).roundToInt()} m"
        } else {
            "${"%.1f".format(distance)} km"
        }
    }
    
    /**
     * Format price for display
     */
    fun formatPrice(price: Double): String {
        return "$${String.format("%.2f", price)}"
    }
}