package ru.magtu.pairs.access

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry




@SpringBootApplication
@EnableCaching
class BotAccessApplication

fun main(args: Array<String>) {
    runApplication<BotAccessApplication>(*args)
}
