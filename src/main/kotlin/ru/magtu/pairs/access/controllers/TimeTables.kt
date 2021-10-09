package ru.magtu.pairs.access.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import ru.magtu.pairs.access.UnknownTokenException
import ru.magtu.pairs.access.repositories.TimeTablesRepository
import ru.magtu.pairs.access.repositories.TokensRepository
import ru.magtu.pairs.access.responses.TimeTablesResponse
import java.time.LocalDateTime
import kotlin.math.abs

@RestController
@RequestMapping("/tables")
class TimeTables(
    val timeTablesRepository: TimeTablesRepository,
    val tokensRepository: TokensRepository
) {
    @GetMapping("/all")
    fun timeTables(@RequestHeader("token") token: String) =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownTokenException()))
            .flatMap {
                timeTablesRepository.findAll()
                    .map {
                        it.toTimeTableItem()
                    }.collectList()
            }
            .map {
                TimeTablesResponse(it)
            }


    @GetMapping("/latest")
    fun latestTimeTables() =
        timeTablesRepository.findAllByDateBetween()
            .map {
                it.toTimeTableItem()
            }.collectList()
            .map {
                TimeTablesResponse(it)
            }

    @GetMapping("/all/{groupName}")
    fun groupTimeTables(
        @RequestHeader("token") token: String,
        @PathVariable("groupName") name: String
    ): Mono<TimeTablesResponse> =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownTokenException()))
            .flatMap {
                timeTablesRepository.findByGroupIsContaining(name).map {
                    it.toTimeTableItem()
                }.collectList()
            }
            .filter { it.isNotEmpty() }
            .switchIfEmpty(Mono.defer {
                timeTablesRepository.findByGroupIsContaining(name).map {
                    it.toTimeTableItem()
                }.collectList()
                    .filter { it.isNotEmpty() }
                    .switchIfEmpty(Mono.error(UnknownGroup()))
            })
            .map {
                TimeTablesResponse(it)
            }

    @GetMapping("/latest/{groupName}")
    fun groupLatestTimeTables(@PathVariable("groupName") name: String): Mono<TimeTablesResponse> =
        timeTablesRepository.findByGroupIsContainingAndDateBetween(name)
            .map {
                it.toTimeTableItem()
            }.collectList()
            .filter { it.isNotEmpty() }
            .switchIfEmpty(Mono.defer {
                timeTablesRepository.findByDisplayNameAndDateBetween(name).map {
                    it.toTimeTableItem()
                }.collectList()
                    .filter { it.isNotEmpty() }
                    .switchIfEmpty(Mono.error(UnknownGroup()))
            })
            .map {
                TimeTablesResponse(it)
            }
}


@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Group doesn't exists")
class UnknownGroup : Exception()
