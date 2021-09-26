package ru.magtu.pairs.access.controllers

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import ru.magtu.pairs.access.repositories.UsersRepository
import ru.magtu.pairs.access.responses.UsersResponse

@CrossOrigin(origins = ["http://localhost:3123"])
@RestController
@RequestMapping("/users")
class Users(
    val usersRepository: UsersRepository
) {
    @GetMapping()
    fun users() = usersRepository.findAll()
        .map {
            it.toUserItem()
        }.collectList()
        .map {
            UsersResponse(it)
        }
}
