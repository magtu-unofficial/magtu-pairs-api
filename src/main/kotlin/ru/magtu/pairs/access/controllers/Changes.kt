package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import ru.magtu.pairs.access.repositories.ChangesRepository
import ru.magtu.pairs.access.responses.ChangesResponse
import ru.magtu.pairs.access.responses.TimeTablesResponse

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
