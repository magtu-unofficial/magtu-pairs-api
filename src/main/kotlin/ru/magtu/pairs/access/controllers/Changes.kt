package ru.magtu.pairs.access.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.magtu.pairs.access.UnknownTokenException
import ru.magtu.pairs.access.repositories.ChangesRepository
import ru.magtu.pairs.access.repositories.TokensRepository
import ru.magtu.pairs.access.responses.ChangesResponse

@RestController
@RequestMapping("/changes")
class Changes(
    val changesRepository: ChangesRepository,
    val tokensRepository: TokensRepository
) {
    @GetMapping("/all")
    fun changes(@RequestHeader("token") token: String) =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownTokenException()))
            .flatMap {
                changesRepository.findAll().map {
                    it.toChangeItem()
                }.collectList()
            }
            .map { ChangesResponse(it) }

    @GetMapping("/all/{fileName}")
    fun fileChanges(
        @RequestHeader("token") token: String, @PathVariable("fileName") fileName: String) =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownTokenException()))
            .flatMap {
                changesRepository.findByFileName(fileName)
            }
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

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "File doesn't exists")
class UnknownFile : Exception()
