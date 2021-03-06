package ru.magtu.pairs.access.responses

import ru.magtu.pairs.access.repositories.Pairs
import java.time.LocalDateTime
import java.util.*

data class TimeTableItem(
    val group: List<String> = Collections.emptyList(),

    val date: LocalDateTime,

    val pairs: List<Pairs> = Collections.emptyList(),

    val displayName: String,
)
