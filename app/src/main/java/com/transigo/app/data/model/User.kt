package com.transigo.app.data.model

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val userType: UserType = UserType.CUSTOMER,
    val createdAt: Long = System.currentTimeMillis(),
    val fcmToken: String = "", // Firebase Cloud Messaging token for push notifications
    val notificationsEnabled: Boolean = true // User preference for receiving push notifications
)

enum class UserType {
    CUSTOMER,
    DRIVER,
    ADMIN
}
