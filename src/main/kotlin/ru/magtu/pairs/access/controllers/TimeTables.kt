package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.magtu.pairs.access.repositories.TimeTablesRepository
import ru.magtu.pairs.access.responses.TimeTablesResponse

@CrossOrigin(origins = ["http://localhost:3123"])
@RestController
@RequestMapping("/tables")
class TimeTables(
    val timeTablesRepository: TimeTablesRepository
) {
    @GetMapping
    fun timeTables() =
        timeTablesRepository.findAll()
            .map {
                it.toTimeTableItem()
            }.collectList()
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

    @GetMapping("/{groupName}")
    fun groupTimeTables(@PathVariable("groupName") name: String): Mono<TimeTablesResponse> =
        timeTablesRepository.findByGroupIsContaining(name)
            .map {
                it.toTimeTableItem()
            }.collectList()
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

class UnknownGroup : Exception()
