package ua.kpi.its.lab.rest.svc

import jakarta.persistence.EntityNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ua.kpi.its.lab.rest.dto.CarRequest
import ua.kpi.its.lab.rest.dto.CarResponse
import ua.kpi.its.lab.rest.entity.Battery
import ua.kpi.its.lab.rest.entity.Car
import ua.kpi.its.lab.rest.repo.BatteryRepository
import ua.kpi.its.lab.rest.repo.CarRepository

@Service
class CarService @Autowired constructor(
    private val carRepository: CarRepository,
    private val batteryRepository: BatteryRepository
) {

    /**
     * This method saves input [Car] entity into DB
     *
     * @param [entity] [CarRequest] object to be saved in DB
     * @return saved [CarResponse] object
     */
    fun save(entity: CarRequest): CarResponse {
        val car = mapCarRequestToCar(entity)
        val savedCar = carRepository.save(car)
        return mapCarToCarResponse(savedCar)
    }

    /**
     * This method returns [Car] object from DB by input id
     *
     * @param [id] [Car] object id in DB
     * @return Option of [CarResponse] object
     */
    fun findById(id: Long): CarResponse? {
        val car = carRepository.findById(id)
        return car.map { mapCarToCarResponse(it) }.orElse(null)
    }

    /**
     * This method returns [List] of [Car] objects from DB
     *
     * @return [List] of [CarResponse] objects
     */
    fun findAll(): List<CarResponse> {
        return carRepository.findAll()
            .map { car: Car -> mapCarToCarResponse(car) }
    }

    /**
     * This method updates [Car] entity from DB
     *
     * @param [entity] [CarRequest] object to be updated in DB
     * @return updated [CarResponse] object
     */
    fun update(id: Long, entity: CarRequest): CarResponse {
        val carToUpdate = mapCarRequestToCar(entity)
        carToUpdate.id = id
        val updatedCar = carRepository.save(carToUpdate)
        return mapCarToCarResponse(updatedCar)
    }

    /**
     * This deletes [Car] entity from DB
     *
     * @param [id] [Car] object id in DB
     * @return updated [Car] object
     */
    fun deleteById(id: Long) {
        carRepository.deleteById(id)
    }

    /**
     * This method checks if  [Car] entity from DB exists by provided id
     *
     * @param [id] [Car] object id in DB
     * @return true if [Car] exists
     */
    fun existsById(id: Long): Boolean {
        return carRepository.existsById(id)
    }

    /**
     * Map a [CarRequest] DTO object to a [Car] entity
     * @param carRequest [CarRequest] DTO object to be mapped
     * @return [Car] object
     */
    fun mapCarRequestToCar(carRequest: CarRequest): Car {
        val battery = batteryRepository.findById(carRequest.batteryId)
            .orElseThrow { EntityNotFoundException("Battery with id ${carRequest.batteryId} not found") }
        return Car(
            brand = carRequest.brand,
            model = carRequest.model,
            manufacturer = carRequest.manufacturer,
            productionDate = carRequest.productionDate,
            maxSpeed = carRequest.maxSpeed,
            price = carRequest.price,
            absAvailable = carRequest.absAvailable,
            battery = battery
        )
    }

    /**
     * Map a [Car] entity to a [CarResponse] DTO entity.
     * @param car [Car] object to be mapped
     * @return [CarResponse] object
     */
    fun mapCarToCarResponse(car: Car): CarResponse {
        return CarResponse(
            id = car.id!!,
            brand = car.brand,
            model = car.model,
            manufacturer = car.manufacturer,
            productionDate = car.productionDate,
            maxSpeed = car.maxSpeed,
            price = car.price,
            absAvailable = car.absAvailable,
            battery = car.battery
        )
    }
}
