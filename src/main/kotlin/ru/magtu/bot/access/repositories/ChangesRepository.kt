package ru.magtu.bot.access.repositories

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface ChangesRepository : ReactiveMongoRepository<ChangeDocument, Int>
