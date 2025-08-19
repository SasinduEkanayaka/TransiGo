package com.transigo.app.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId

data class Driver(
    @DocumentId
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val vehicleType: VehicleType = VehicleType.CAR,
    val vehicleInfo: VehicleInfo = VehicleInfo(),
    val rating: Double = 0.0,
    val totalRides: Int = 0,
    val vehicleNumber: String = "",
    val isActive: Boolean = true,
    val createdAt: Timestamp? = null,
    val updatedAt: Timestamp? = null
)

enum class VehicleType {
    CAR, VAN, BUS;
    
    override fun toString(): String {
        return name
    }
}

data class VehicleInfo(
    val make: String = "",
    val model: String = "",
    val year: Int = 0,
    val color: String = "",
    val licensePlate: String = "",
    val capacity: Int = 4
)
