package ru.magtu.pairs.access.repositories

import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface TokensRepository : ReactiveMongoRepository<TokenDocument, Int> {
    fun findByToken(token: String): Mono<TokenDocument>
}


