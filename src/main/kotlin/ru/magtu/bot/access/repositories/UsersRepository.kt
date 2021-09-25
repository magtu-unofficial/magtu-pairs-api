package ru.magtu.bot.access.repositories

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface UsersRepository : ReactiveMongoRepository<UserDocument, Int>
