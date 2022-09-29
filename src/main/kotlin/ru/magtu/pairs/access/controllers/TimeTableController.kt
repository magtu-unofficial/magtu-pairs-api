package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
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
            .filter { it.pairs.isNotEmpty() }
            .map { it.toTimeTableItem() }
            .collectList()
            .map { TimeTablesResponse(it) }


    @GetMapping("/latest")
    fun latestTimeTables() =
        timeTablesRepository.findAllByDateBetween()
            .filter { it.pairs.isNotEmpty() }
            .map { it.toTimeTableItem() }
            .collectList()
            .map { TimeTablesResponse(it) }

    @GetMapping("/all/{groupName}")
    fun groupTimeTables(@RequestHeader("token") token: String, @PathVariable("groupName") name: String): Mono<TimeTablesResponse> =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownTokenException()))
            .flatMapMany { timeTablesRepository.findByGroupIsContaining(name) }
            .filter { it.pairs.isNotEmpty() }
            .map { it.toTimeTableItem() }
            .collectList()
            .map { TimeTablesResponse(it) }

    @GetMapping("/latest/{groupName}")
    fun groupLatestTimeTables(@PathVariable("groupName") name: String): Mono<TimeTablesResponse> =
        timeTablesRepository.findByDisplayNameAndDateBetween(name)
            .filter { it.pairs.isNotEmpty() }
            .map { it.toTimeTableItem() }
            .collectList()
            .map { TimeTablesResponse(it) }

    @GetMapping("/teacher/{teacher}")
    fun getByTeacher(@PathVariable("teacher") teacher: String) =
        timeTablesRepository.findAllByDateBetween()
            .filter { it.pairs.isNotEmpty() }
            .map { Pair(it.displayName, hashMapOf(it.date to it.pairs.filter { it.teacher == teacher })) }
            .filter { it.second.values.any { it.isNotEmpty() } }
}
