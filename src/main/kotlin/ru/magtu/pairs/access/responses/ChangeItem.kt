package ru.magtu.pairs.access.responses

import java.time.LocalDateTime

data class ChangeItem(
    val date: LocalDateTime,

    val time: Int,

    val fileName: String,

    val md5: String,
)
