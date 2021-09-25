package ru.magtu.bot.access.responses

import ru.magtu.bot.access.repositories.Pairs
import java.time.LocalDateTime
import java.util.*

data class TimeTableItem(
    val group: List<String> = Collections.emptyList(),

    val date: LocalDateTime,

    val pairs: List<Pairs> = Collections.emptyList()
)
