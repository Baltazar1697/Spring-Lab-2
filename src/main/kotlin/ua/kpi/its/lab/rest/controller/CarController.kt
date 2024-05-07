package ua.kpi.its.lab.rest.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ua.kpi.its.lab.rest.dto.CarRequest
import ua.kpi.its.lab.rest.dto.CarResponse
import ua.kpi.its.lab.rest.entity.Car
import ua.kpi.its.lab.rest.svc.CarService

/**
 * Controller for managing Car entities.
 */
@RestController
@RequestMapping("/cars")
class CarController(
    private val carService: CarService
) {

    /**
     * Create a new [Car] entity
     * @param carRequest DTO object containing data for the new car
     * @return [CarResponse] DTO object representing the created battery
     */
    @PostMapping("/")
    fun createCar(@RequestBody carRequest: CarRequest): CarResponse {
        return carService.save(carRequest)
    }

    /**
     * Get [Car] entity by given id
     * @param id id of [Car] entity
     * @return [CarResponse] DTO object representing found car
     */
    @GetMapping("/{id}")
    fun getCar(@PathVariable id: Long): ResponseEntity<CarResponse> {
        val foundCar = carService.findById(id)
        return if (foundCar != null) {
            ResponseEntity.ok(foundCar)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    /**
     * Get [List] of [Car] entities
     * @return [List] of DTO object representing found cars
     */
    @GetMapping("/")
    fun getCars(): List<CarResponse> {
        return carService.findAll()
    }

    /**
     * Update [Car] entity by given id
     * @param id id of [Car] entity
     * @param carRequest DTO object containing data for the updated car
     * @return [CarResponse] DTO object representing the updated car
     */
    @PutMapping("/{id}")
    fun updateCar(@PathVariable id: Long, @RequestBody carRequest: CarRequest): ResponseEntity<CarResponse> {
        if (!carService.existsById(id)) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(carService.update(id, carRequest))
    }

    /**
     * Delete [Car] entity by given id
     * @param id id of [Car] entity
     */
    @DeleteMapping("/{id}")
    fun deleteCar(@PathVariable id: Long) {
        carService.deleteById(id)
    }
}
