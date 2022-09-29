package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
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
            .flatMapMany { changesRepository.findAll() }
            .map { it.toChangeItem() }
            .collectList()
            .map { ChangesResponse(it) }

    @GetMapping("/all/{fileName}")
    fun fileChanges(
        @RequestHeader("token") token: String, @PathVariable("fileName") fileName: String) =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownTokenException()))
            .flatMap { changesRepository.findByFileName(fileName) }
            .map { it.toChangeItem() }

    @GetMapping("/latest")
    fun latestChanges() =
        changesRepository.findByDateBetween()
            .map { it.toChangeItem() }
            .collectList()
            .map { ChangesResponse(it) }
}
