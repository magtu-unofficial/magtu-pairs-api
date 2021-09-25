package ru.magtu.pairs.access.repositories

import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface TimeTablesRepository : ReactiveMongoRepository<TimeTableDocument, Int>
