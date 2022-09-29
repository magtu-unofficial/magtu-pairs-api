package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import ru.magtu.pairs.access.repositories.TokensRepository
import ru.magtu.pairs.access.repositories.UsersRepository
import ru.magtu.pairs.access.responses.UsersResponse

@RestController
@RequestMapping("/users/all")
class UserController(
    val usersRepository: UsersRepository,
    val tokensRepository: TokensRepository
) {
    @GetMapping
    fun users(@RequestHeader("token") token: String) =
        tokensRepository.findByToken(token)
            .switchIfEmpty(Mono.error(UnknownTokenException()))
            .flatMapMany { usersRepository.findAll() }
            .map { it.toUserItem() }
            .collectList()
            .map { UsersResponse(it) }

}
