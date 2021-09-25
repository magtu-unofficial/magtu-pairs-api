package ru.magtu.bot.access.repositories

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import ru.magtu.bot.access.responses.ChangeItem
import java.time.LocalDateTime

@Document("changes")
data class ChangeDocument(
    @Id
    val id: String,

    @Field
    val date: LocalDateTime,

    @Field
    val time: Int,

    @Field
    val fileName: String,

    @Field
    val md5: String,
) {
    fun toChangeItem() = ChangeItem(
        date = this.date,
        time = this.time,
        fileName = this.fileName,
        md5 = this.md5,
    )
}
