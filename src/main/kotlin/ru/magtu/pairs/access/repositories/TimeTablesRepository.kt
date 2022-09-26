package ru.magtu.pairs.access.repositories


import org.springframework.data.mongodb.repository.Query
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import java.time.LocalDateTime

@Repository
interface TimeTablesRepository : ReactiveMongoRepository<TimeTableDocument, Int> {

    fun findByGroupIsContaining(
        group: String,
    ): Flux<TimeTableDocument>

    fun findByDisplayNameAndDateBetween(
        displayName: String,
        oldDate: LocalDateTime? = LocalDateTime.now().minusDays(1),
        currentDate: LocalDateTime? = LocalDateTime.now().plusDays(3),
    ): Flux<TimeTableDocument>

    fun findAllByDateBetween(
        oldDate: LocalDateTime? = LocalDateTime.now().minusDays(7),
        currentDate: LocalDateTime? = LocalDateTime.now()
    ): Flux<TimeTableDocument>

}
