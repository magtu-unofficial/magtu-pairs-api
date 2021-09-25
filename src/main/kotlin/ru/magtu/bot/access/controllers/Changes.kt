package ru.magtu.bot.access.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import ru.magtu.bot.access.repositories.ChangesRepository
import ru.magtu.bot.access.responses.ChangesResponse
import ru.magtu.bot.access.responses.TimeTablesResponse

@RestController
@RequestMapping("/changes")
class Changes(
    val changesRepository: ChangesRepository
) {
    @GetMapping
    fun changes() = Flux.merge(
        changesRepository.findAll().map {
            it.toChangeItem()
        }
    ).collectList()
        .map {
            ChangesResponse(it)
        }
}
