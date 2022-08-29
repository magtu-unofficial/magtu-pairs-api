package ru.magtu.pairs.access.repositories

import org.springframework.data.mongodb.core.mapping.Document

@Document("tokens")
data class TokenDocument(val token: String)