package ru.magtu.pairs.access.repositories


import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import kotlin.math.abs

interface TimeTablesRepository : ReactiveMongoRepository<TimeTableDocument, Int> {

    fun findByGroupIsContaining(
        group: String,
    ): Flux<TimeTableDocument>

    fun findByDisplayNameAndDateBetween(
        displayName: String,
        oldDate: LocalDateTime? = LocalDateTime.now(),
        currentDate: LocalDateTime? = LocalDateTime.now().plusDays(7),
    ): Flux<TimeTableDocument>

    fun findAllByDateBetween(
        oldDate: LocalDateTime? = LocalDateTime.now().minusDays(7),
        currentDate: LocalDateTime? = LocalDateTime.now()
    ): Flux<TimeTableDocument>

}
