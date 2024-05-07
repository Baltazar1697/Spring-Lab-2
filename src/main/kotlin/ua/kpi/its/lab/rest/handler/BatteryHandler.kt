package ua.kpi.its.lab.rest.handler

import org.springframework.stereotype.Component
import org.springframework.web.servlet.function.ServerRequest
import org.springframework.web.servlet.function.ServerResponse
import ua.kpi.its.lab.rest.dto.BatteryRequest
import ua.kpi.its.lab.rest.svc.BatteryService

@Component
class BatteryHandler(private val batteryService: BatteryService) {

    fun create(request: ServerRequest): ServerResponse {
        val batteryDto = request.body(BatteryRequest::class.java)

        return ServerResponse.ok().body(batteryService.save(batteryDto))
    }

    fun getById(request: ServerRequest): ServerResponse {
        val batteryId = request.pathVariable("id").toLong()

        val foundBattery = batteryService.findById(batteryId)
        return if (foundBattery != null) {
            ServerResponse.ok().body(foundBattery)
        } else {
            ServerResponse.notFound().build()
        }
    }

    fun getAll(request: ServerRequest): ServerResponse {
        return ServerResponse.ok().body(batteryService.findAll())
    }

    fun update(request: ServerRequest): ServerResponse {
        val batteryId = request.pathVariable("id").toLong()
        val batteryDto = request.body(BatteryRequest::class.java)

        return if (!batteryService.existsById(batteryId)) {
            ServerResponse.notFound().build()
        } else {
            ServerResponse.ok().body(batteryService.update(batteryId, batteryDto))
        }
    }

    fun delete(request: ServerRequest): ServerResponse {
        val batteryId = request.pathVariable("id").toLong()

        batteryService.deleteById(batteryId)
        return ServerResponse.noContent().build()
    }
}