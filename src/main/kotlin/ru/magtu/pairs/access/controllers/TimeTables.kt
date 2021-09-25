package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import ru.magtu.pairs.access.repositories.TimeTablesRepository
import ru.magtu.pairs.access.responses.TimeTablesResponse

@RestController
@RequestMapping("/tables")
class TimeTables(
    val timeTablesRepository: TimeTablesRepository
) {
    @GetMapping
    fun timeTables() = Flux.merge(
        timeTablesRepository.findAll().map {
            it.toTimeTableItem()
        }
    ).collectList()
        .map {
            TimeTablesResponse(it)
        }
}
