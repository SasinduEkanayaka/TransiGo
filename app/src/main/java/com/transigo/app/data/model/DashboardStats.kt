package com.transigo.app.data.model

data class DashboardStats(
    val totalBookings: Int = 0,
    val bookingsToday: Int = 0,
    val completedThisMonth: Int = 0,
    val activeDrivers: Int = 0,
    val pendingRequestsCount: Int = 0
)
