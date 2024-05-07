package ua.kpi.its.lab.rest.dto

import java.util.*

data class BatteryRequest(
    val model: String,
    val manufacturer: String,
    val type: String,
    val capacity: Int,
    val productionDate: Date,
    val chargeTime: Int,
    val fastChargeAvailable: Boolean
)

data class BatteryResponse(
    val id: Long,
    val model: String,
    val manufacturer: String,
    val type: String,
    val capacity: Int,
    val productionDate: Date,
    val chargeTime: Int,
    val fastChargeAvailable: Boolean
)