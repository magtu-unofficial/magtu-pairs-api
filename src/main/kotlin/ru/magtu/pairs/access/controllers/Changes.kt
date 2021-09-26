package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.magtu.pairs.access.repositories.ChangesRepository
import ru.magtu.pairs.access.repositories.TokensRepository
import ru.magtu.pairs.access.responses.ChangesResponse

@CrossOrigin(origins = ["http://localhost:3123"])
@RestController
@RequestMapping("/changes")
class Changes(
    val changesRepository: ChangesRepository,
    val tokensRepository: TokensRepository
) {
    @GetMapping
    fun changes(@RequestHeader("token") token: String) =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownToken()))
            .flatMap { _ ->
                changesRepository.findAll().map {
                    it.toChangeItem()
                }.collectList()
            }
            .map {
                ChangesResponse(it)
            }

    @GetMapping("/{fileName}")
    fun fileChanges(@PathVariable("fileName") fileName: String) =
        changesRepository.findByFileName(fileName)
            .switchIfEmpty(Mono.error(UnknownFile()))
            .map {
                it.toChangeItem()
            }

    @GetMapping("/latest")
    fun latestChanges() =
        changesRepository.findByDateBetween()
            .switchIfEmpty(Mono.error(UnknownFile()))
            .map {
                it.toChangeItem()
            }
}

class UnknownFile : Exception()
