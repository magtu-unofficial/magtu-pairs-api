package ru.magtu.pairs.access.repositories

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import ru.magtu.pairs.access.responses.TimeTableItem
import java.time.LocalDateTime
import java.util.*

@Document("timetables")
data class TimeTableDocument(
    @Id
    val id: String,

    @Field
    val group: List<String> = Collections.emptyList(),

    @Field
    val date: LocalDateTime,

    @Field
    val pairs: List<Pairs> = Collections.emptyList()

) {
    fun toTimeTableItem() = TimeTableItem(
        group = this.group,
        date = this.date,
        pairs = this.pairs,
    )
}

data class Pairs(
    val changes: Boolean? = false,

    val removed: Boolean? = false,

    val error: Boolean? = false,

    val number: Int? = null,

    val subgroup: String? = null,

    val name: String? = null,

    val teacher: String? = null,

    val classroom: String? = null
)
