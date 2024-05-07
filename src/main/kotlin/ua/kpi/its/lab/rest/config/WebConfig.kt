package ua.kpi.its.lab.rest.config

import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import org.springframework.web.servlet.function.router
import ua.kpi.its.lab.rest.handler.BatteryHandler
import ua.kpi.its.lab.rest.handler.CarHandler
import java.text.SimpleDateFormat

@Configuration
@EnableWebMvc
class WebConfig : WebMvcConfigurer {

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>>) {
        val builder = Jackson2ObjectMapperBuilder()
            .indentOutput(true)
            .dateFormat(SimpleDateFormat("yyyy-MM-dd"))
            .modulesToInstall(KotlinModule.Builder().build())

        converters
            .add(MappingJackson2HttpMessageConverter(builder.build()))
    }

    @Bean
    fun functionalRoutes(
        batteryHandler: BatteryHandler,
        carHandler: CarHandler
    ): RouterFunction<ServerResponse> = router {
        "/fn".nest {
            "/batteries".nest {
                GET("/", batteryHandler::getAll)
                POST("/", batteryHandler::create)
                GET("/{id}", batteryHandler::getById)
                PUT("/{id}", batteryHandler::update)
                DELETE("/{id}", batteryHandler::delete)
            }
            "/cars".nest {
                GET("/", carHandler::getAll)
                POST("/", carHandler::create)
                GET("/{id}", carHandler::getById)
                PUT("/{id}", carHandler::update)
                DELETE("/{id}", carHandler::delete)
            }
        }
    }
}