package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.magtu.pairs.access.repositories.TokensRepository
import ru.magtu.pairs.access.repositories.UsersRepository
import ru.magtu.pairs.access.responses.UsersResponse

@CrossOrigin(origins = ["http://localhost:3123"])
@RestController
@RequestMapping("/users")
class Users(
    val usersRepository: UsersRepository,
    val tokensRepository: TokensRepository
) {
    @GetMapping
    fun users(@RequestHeader("token") token: String) =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownToken()))
            .flatMap {
                usersRepository.findAll()
                    .map {
                        it.toUserItem()
                    }.collectList()
            }.map {
                UsersResponse(it)
            }

}
