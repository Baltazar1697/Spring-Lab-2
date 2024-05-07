package ua.kpi.its.lab.rest.controller

import org.springframework.http.ResponseEntity
import ua.kpi.its.lab.rest.svc.BatteryService
import org.springframework.web.bind.annotation.*
import ua.kpi.its.lab.rest.dto.BatteryRequest
import ua.kpi.its.lab.rest.dto.BatteryResponse
import ua.kpi.its.lab.rest.entity.Battery

/**
 * Controller for managing Battery entities
 */
@RestController
@RequestMapping("/batteries")
class BatteryController(private val batteryService: BatteryService) {

    /**
     * Create a new [Battery] entity
     * @param batteryRequest DTO object containing data for the new battery
     * @return DTO object representing the created battery
     */
    @PostMapping("/")
    fun createBattery(@RequestBody batteryRequest: BatteryRequest): BatteryResponse {
        return batteryService.save(batteryRequest)
    }

    /**
     * Get [Battery] entity by given id
     * @param id id of [Battery] entity
     * @return DTO object representing found battery
     */
    @GetMapping("/{id}")
    fun getBattery(@PathVariable id: Long): ResponseEntity<BatteryResponse> {
        val foundBattery = batteryService.findById(id)
        return if (foundBattery != null) {
            ResponseEntity.ok(foundBattery)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Get [List] of [Battery] entities
     * @return [List] of DTO object representing found batteries
     */
    @GetMapping("/")
    fun getBatteries(): List<BatteryResponse> {
        return batteryService.findAll()
    }

    /**
     * Update [Battery] entity by given id
     * @param id id of [Battery] entity
     * @param batteryRequest DTO object containing data for the updated battery
     * @return DTO object representing the updated battery
     */
    @PutMapping("/{id}")
    fun updateBattery(
        @PathVariable id: Long,
        @RequestBody batteryRequest: BatteryRequest
    ): ResponseEntity<BatteryResponse> {
        return if (!batteryService.existsById(id)) {
            ResponseEntity.notFound().build()
        } else {
            ResponseEntity.ok(batteryService.update(id, batteryRequest))
        }
    }

    /**
     * Delete [Battery] entity by given id
     * @param id id of [Battery] entity
     */
    @DeleteMapping("/{id}")
    fun deleteBattery(@PathVariable id: Long) {
        batteryService.deleteById(id)
    }
}