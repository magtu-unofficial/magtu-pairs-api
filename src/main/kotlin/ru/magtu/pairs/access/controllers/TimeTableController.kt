package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.magtu.pairs.access.UnknownTokenException
import ru.magtu.pairs.access.repositories.TimeTablesRepository
import ru.magtu.pairs.access.repositories.TokensRepository
import ru.magtu.pairs.access.responses.TimeTablesResponse

@RestController
@RequestMapping("/tables")
class TimeTableController(
    val timeTablesRepository: TimeTablesRepository,
    val tokensRepository: TokensRepository
) {
    @GetMapping("/all")
    fun timeTables(@RequestHeader("token") token: String) =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownTokenException()))
            .flatMapMany { timeTablesRepository.findAll() }
            .map { it.toTimeTableItem() }
            .collectList()
            .map { TimeTablesResponse(it) }


    @GetMapping("/latest")
    fun latestTimeTables() =
        timeTablesRepository.findAllByDateBetween()
            .map { it.toTimeTableItem() }
            .collectList()
            .map { TimeTablesResponse(it) }

    @GetMapping("/all/{groupName}")
    fun groupTimeTables(@RequestHeader("token") token: String, @PathVariable("groupName") name: String): Mono<TimeTablesResponse> =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownTokenException()))
            .flatMapMany { timeTablesRepository.findByGroupIsContaining(name) }
            .map { it.toTimeTableItem() }
            .collectList()
            .map { TimeTablesResponse(it) }

    @GetMapping("/latest/{groupName}")
    fun groupLatestTimeTables(@PathVariable("groupName") name: String): Mono<TimeTablesResponse> =
        timeTablesRepository.findByDisplayNameAndDateBetween(name)
            .map { it.toTimeTableItem() }
            .collectList()
            .map { TimeTablesResponse(it) }
}
