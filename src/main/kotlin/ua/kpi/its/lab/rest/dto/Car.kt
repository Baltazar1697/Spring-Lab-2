package ua.kpi.its.lab.rest.dto

import ua.kpi.its.lab.rest.entity.Battery
import java.util.*

data class CarRequest(
    val brand: String,
    val model: String,
    val manufacturer: String,
    val productionDate: Date,
    val maxSpeed: Int,
    val price: Double,
    val absAvailable: Boolean,
    val batteryId: Long
)

data class CarResponse(
    val id: Long,
    val brand: String,
    val model: String,
    val manufacturer: String,
    val productionDate: Date,
    val maxSpeed: Int,
    val price: Double,
    val absAvailable: Boolean,
    val battery: Battery
)
