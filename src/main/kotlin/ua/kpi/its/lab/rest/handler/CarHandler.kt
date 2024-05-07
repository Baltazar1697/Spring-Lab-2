package ua.kpi.its.lab.rest.handler

import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import ua.kpi.its.lab.rest.dto.CarRequest
import ua.kpi.its.lab.rest.svc.BatteryService
import ua.kpi.its.lab.rest.svc.CarService

@Component
class CarHandler(private val carService: CarService, private val batteryService: BatteryService) {

    fun create(request: ServerRequest): ServerResponse {
        val carDto = request.body(CarRequest::class.java)

        val car = carService.save(carDto)
        return ServerResponse.ok().body(car)
    }

    fun getById(request: ServerRequest): ServerResponse {
        val carId = request.pathVariable("id").toLong()

        val foundCar = carService.findById(carId)
        return if (foundCar != null) {
            ServerResponse.ok().body(foundCar)
        } else {
            ServerResponse.notFound().build()
        }
    }

    fun getAll(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().body(carService.findAll())
    }

    fun update(request: ServerRequest): ServerResponse {
        val carId = request.pathVariable("id").toLong()
        val carDto = request.body(CarRequest::class.java)

        return if (!carService.existsById(carId) || !batteryService.existsById(carDto.batteryId)) {
            return ServerResponse.notFound().build()
        } else {
            ServerResponse.ok().body(carService.update(carId, carDto))
        }
    }

    fun delete(request: ServerRequest): ServerResponse {
        val carId = request.pathVariable("id").toLong()

        carService.deleteById(carId)
        return ServerResponse.noContent().build()
    }
}