package ua.kpi.its.lab.rest.svc

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.kpi.its.lab.rest.dto.BatteryRequest
import ua.kpi.its.lab.rest.dto.BatteryResponse
import ua.kpi.its.lab.rest.entity.Battery
import ua.kpi.its.lab.rest.repo.BatteryRepository

@Service
class BatteryService @Autowired constructor(
    private val batteryRepository: BatteryRepository
) {
    /**
     * This method saves input [Battery] entity into DB
     *
     * @param [entity] [BatteryRequest] DTO object to be saved in DB
     * @return saved [BatteryResponse] object
     */
    fun save(entity: BatteryRequest): BatteryResponse {
        val battery = mapBatteryRequestToBattery(entity)
        val savedBattery = batteryRepository
            .save(battery)
        return mapBatteryToBatteryResponse(savedBattery)
    }

    /**
     * This method returns [Battery] object from DB by input id
     *
     * @param [id] [Battery] object id in DB
     * @return Option of [BatteryResponse] object
     */
    fun findById(id: Long): BatteryResponse? {
        val battery = batteryRepository.findById(id)
        return battery.map { mapBatteryToBatteryResponse(it) }.orElse(null)
    }


    /**
     * This method returns [List] of [BatteryResponse] objects from DB
     *
     * @return [List] of [BatteryResponse] objects
     */
    fun findAll(): List<BatteryResponse> {
        return batteryRepository.findAll()
            .map { battery: Battery -> mapBatteryToBatteryResponse(battery) }
    }

    /**
     * This method updates [Battery] entity from DB
     *
     * @param [entity] [BatteryRequest] object to be updated in DB
     * @return updated [BatteryResponse] object
     */
    fun update(id: Long, entity: BatteryRequest): BatteryResponse {
        val batteryToUpdate = mapBatteryRequestToBattery(entity)
        batteryToUpdate.id = id
        val updatedBattery = batteryRepository.save(batteryToUpdate)
        return mapBatteryToBatteryResponse(updatedBattery)
    }

    /**
     * This method deletes [Battery] entity from DB
     *
     * @param [id] [Battery] object id in DB
     * @return updated [Battery] object
     */
    fun deleteById(id: Long) {
        batteryRepository.deleteById(id)
    }

    /**
     * This method checks if  [Battery] entity from DB exists by provided id
     *
     * @param [id] [Battery] object id in DB
     * @return true if [Battery] exists
     */
    fun existsById(id: Long): Boolean {
        return batteryRepository.existsById(id)
    }

    /**
     * Map a [BatteryRequest] DTO object to a [Battery] entity
     * @param batteryRequest [BatteryRequest] DTO object to be mapped
     * @return [Battery] object
     */
    private fun mapBatteryRequestToBattery(batteryRequest: BatteryRequest): Battery {
        return Battery(
            model = batteryRequest.model,
            manufacturer = batteryRequest.manufacturer,
            type = batteryRequest.type,
            capacity = batteryRequest.capacity,
            productionDate = batteryRequest.productionDate,
            chargeTime = batteryRequest.chargeTime,
            fastChargeAvailable = batteryRequest.fastChargeAvailable
        )
    }

    /**
     * Map a [Battery] entity to a [BatteryResponse] DTO entity.
     * @param battery [Battery] object to be mapped
     * @return [BatteryResponse] object
     */
    private fun mapBatteryToBatteryResponse(battery: Battery): BatteryResponse {
        return BatteryResponse(
            id = battery.id!!,
            model = battery.model,
            manufacturer = battery.manufacturer,
            type = battery.type,
            capacity = battery.capacity,
            productionDate = battery.productionDate,
            chargeTime = battery.chargeTime,
            fastChargeAvailable = battery.fastChargeAvailable
        )
    }
}
