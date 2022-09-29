package ru.magtu.pairs

import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication(
    scanBasePackages = ["ru.magtu.pairs"]
)
@EnableCaching
class ServerApplication: ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<ServerApplication>(*args)
        }
    }

}

