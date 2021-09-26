package ru.magtu.pairs.access

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry




@SpringBootApplication
class BotAccessApplication

fun main(args: Array<String>) {
    runApplication<BotAccessApplication>(*args)
}
