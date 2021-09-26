package ru.magtu.pairs.access.repositories

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Mono

interface TokensRepository : ReactiveMongoRepository<Token, Int> {
    fun findByToken(token: String): Mono<Token>
}

@Document("tokens")
data class Token(val token: String)
