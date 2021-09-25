package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import ru.magtu.pairs.access.repositories.UsersRepository
import ru.magtu.pairs.access.responses.UsersResponse

@RestController
@RequestMapping("/users")
class Users(
    val usersRepository: UsersRepository
) {
    @GetMapping()
    fun users() = Flux.merge(usersRepository.findAll().map {
        it.toUserItem()
    }).collectList()
        .map {
            UsersResponse(it)
        }
}