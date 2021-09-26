package ru.magtu.pairs.access.repositories

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime

interface ChangesRepository : ReactiveMongoRepository<ChangeDocument, Int> {
    fun findByFileName(fileName: String): Mono<ChangeDocument>

    fun findByDateBetween(
        oldDate: LocalDateTime? = LocalDateTime.now().minusDays(7),
        currentDate: LocalDateTime? = LocalDateTime.now()
    ): Flux<ChangeDocument>
}
