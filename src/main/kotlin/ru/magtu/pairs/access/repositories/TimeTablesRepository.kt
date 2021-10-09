package ru.magtu.pairs.access.repositories


import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import kotlin.math.abs

interface TimeTablesRepository : ReactiveMongoRepository<TimeTableDocument, Int> {

    fun findByGroupIsContaining(
        group: String,
    ): Flux<TimeTableDocument>

    fun findByGroupIsContainingAndDateBetween(
        group: String,
        oldDate: LocalDateTime? = LocalDateTime.now().minusDays((LocalDateTime.now().dayOfWeek.value).toLong()),
        currentDate: LocalDateTime? = LocalDateTime.now().plusDays((abs(LocalDateTime.now().dayOfWeek.value - 7)).toLong()),
    ): Flux<TimeTableDocument>

    fun findByDisplayNameAndDateBetween(
        displayName: String,
        oldDate: LocalDateTime? = LocalDateTime.now().minusDays(3),
        currentDate: LocalDateTime? = LocalDateTime.now().plusDays(3),
    ): Flux<TimeTableDocument>

    fun findAllByDateBetween(
        oldDate: LocalDateTime? = LocalDateTime.now().minusDays(7),
        currentDate: LocalDateTime? = LocalDateTime.now()
    ): Flux<TimeTableDocument>

}
